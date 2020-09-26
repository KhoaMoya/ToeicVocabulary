package com.khoa.toeicvocabulary.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.khoa.toeicvocabulary.MyApplication
import com.khoa.toeicvocabulary.R
import com.khoa.toeicvocabulary.bases.ItemClickListener
import com.khoa.toeicvocabulary.bases.StatisticType
import com.khoa.toeicvocabulary.databinding.FragmentHomeBinding
import com.khoa.toeicvocabulary.models.Category
import com.khoa.toeicvocabulary.ui.detailcategory.DetailCategoryActivity
import com.khoa.toeicvocabulary.ui.listcategory.ListCategoryFragment
import com.khoa.toeicvocabulary.ui.main.MainActivity
import com.khoa.toeicvocabulary.ui.wordstatistics.WordStatisticFragment
import javax.inject.Inject


class HomeFragment : Fragment(), OnClickListener, ItemClickListener<Category> {

    companion object {
        val CATEGORY_KEY = "category key"
    }

    @Inject
    lateinit var mViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(layoutInflater).also {
            mBinding = it
            mViewModel.categoryRcvAdapter.itemClickListener = this
            mBinding.viewmodel = mViewModel
            mBinding.rcvCategory.adapter = mViewModel.categoryRcvAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLineChart()
        initActions()
        subscribeUi()
    }

    private fun initActions() {
        mBinding.txtSeeAllWords.setOnClickListener(this)
        mBinding.txtSeeAllCategories.setOnClickListener(this)
        mBinding.imgEditTarget.setOnClickListener(this)
        mBinding.btnDetailWordToday.setOnClickListener(this)
        mBinding.btnDetailWordWeek.setOnClickListener(this)
        mBinding.btnDetailWordMonth.setOnClickListener(this)
    }

    private fun subscribeUi() {
        with(mViewModel) {
            categoriesRecently.observe(viewLifecycleOwner, {
                mViewModel.categoryRcvAdapter.setCategoriesList(it)
            })
            countWordsLearnedToday.observe(viewLifecycleOwner, {
                mBinding.txtWordTodayValue.text = it.toString()
            })
            countWordsLearnedThisWeek.observe(viewLifecycleOwner, {
                mBinding.txtWordWeekValue.text = it.toString()
            })
            countWordsLearnedThisMonth.observe(viewLifecycleOwner, {
                mBinding.txtWordMonthValue.text = it.toString()
            })
            countAllWordsLearned.observe(viewLifecycleOwner, {
                mBinding.txtRatioAllWordsLearned.text = "$it/${mViewModel.countAllWords.value}"
            })
            countAllWords.observe(viewLifecycleOwner, {
                mBinding.txtRatioAllWordsLearned.text =
                    "${mViewModel.countAllWordsLearned.value}/$it"
            })
            isLoading.observe(viewLifecycleOwner, { isVisible ->
                if (isVisible) mBinding.progressLoading.visibility = View.VISIBLE
                else mBinding.progressLoading.visibility = View.GONE
            })
            countWordInDayRecently.forEach { dayWord ->
                dayWord.count.observe(viewLifecycleOwner, {
                    showLineChart(mViewModel.countWordInDayRecently)
                })
            }
            targetWordsPerDay.observe(viewLifecycleOwner, {
                mBinding.txtTargetValue.text = it.toString()
                addLimitLine(it)
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtSeeAllWords -> startDetailCategoryActivity(null)
            R.id.txtSeeAllCategories -> {
                (activity as MainActivity).dispatcherTo(ListCategoryFragment())
            }
            R.id.btnDetailWordToday -> {
                (activity as MainActivity).dispatcherTo(WordStatisticFragment(StatisticType.TODAY))
            }
            R.id.btnDetailWordWeek -> {
                (activity as MainActivity).dispatcherTo(WordStatisticFragment(StatisticType.WEEK))
            }
            R.id.btnDetailWordMonth -> {
                (activity as MainActivity).dispatcherTo(WordStatisticFragment(StatisticType.MONTH))
            }
            R.id.imgEditTarget -> showEnterTargetDialog()
        }
    }


    override fun onClickItem(item: Category) {
        startDetailCategoryActivity(item)
    }

    private fun startDetailCategoryActivity(category: Category?) {
        val intent = Intent(activity, DetailCategoryActivity::class.java)
        intent.putExtra(CATEGORY_KEY, category)
        startActivity(intent)
    }

    private fun setupLineChart() {
        mBinding.lineChart.setViewPortOffsets(70f, 0f, 70f, 60f)
        mBinding.lineChart.setBackgroundColor(Color.TRANSPARENT)
        mBinding.lineChart.axisLeft.setStartAtZero(true)
        mBinding.lineChart.description.isEnabled = false

        mBinding.lineChart.setTouchEnabled(false)
        mBinding.lineChart.setDrawGridBackground(false)

        mBinding.lineChart.xAxis.isEnabled = true
        val x: XAxis = mBinding.lineChart.xAxis
        x.textColor = Color.BLACK
        x.position = XAxis.XAxisPosition.BOTTOM
        x.setDrawGridLines(false)
        x.gridColor = ContextCompat.getColor(activity!!, R.color.colorBlack20)
        x.textSize = 10f

        val y: YAxis = mBinding.lineChart.axisLeft
        y.isEnabled = true
        y.setDrawGridLines(true)
        y.textSize = 10f
        y.gridColor = ContextCompat.getColor(activity!!, R.color.colorBlack20)

        mBinding.lineChart.axisRight.isEnabled = false
        mBinding.lineChart.legend.isEnabled = false

//        mBinding.lineChart.animateX(1500)

        mBinding.lineChart.isScaleYEnabled = false
    }

    private fun showLineChart(dayWords: ArrayList<DayWord>) {
        val entries: ArrayList<Entry> = ArrayList()
        dayWords.forEachIndexed { index, dayWord ->
            entries.add(Entry(index.toFloat(), dayWord.count.value?.toFloat() ?: 0f))
        }

        val lineDataSet = LineDataSet(entries, "tracking learn recently days")
        lineDataSet.mode = LineDataSet.Mode.LINEAR
//         lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.lineWidth = 3f


        // set the filled area
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillFormatter = IFillFormatter { _, _ ->
            mBinding.lineChart.axisLeft.axisMinimum
        }
        lineDataSet.color = ContextCompat.getColor(activity!!, R.color.colorAccent)
        lineDataSet.fillDrawable =
            ContextCompat.getDrawable(activity!!, R.drawable.bg_gradient)
        lineDataSet.setDrawValues(true)

//        lineDataSet.setFillColor(getResources().getColor(R.color.colorPrimary));
        lineDataSet.fillAlpha = 10

        lineDataSet.setDrawCircles(true)
        lineDataSet.circleRadius = 3f
        lineDataSet.setCircleColor(ContextCompat.getColor(activity!!, R.color.colorAccent))

        lineDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        val data = LineData(lineDataSet)
        data.setValueTextSize(10f)
        data.setDrawValues(true)

        // set label cho truc X
        mBinding.lineChart.xAxis.valueFormatter =
            object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase): String {
                    return dayWords[value.toInt()].dateName
                }
            }

        // set data
        mBinding.lineChart.data = data
        mBinding.lineChart.invalidate()
    }

    private fun addLimitLine(limit: Int) {
        mBinding.lineChart.axisLeft.removeAllLimitLines()
        if (limit > 0) {
            mBinding.lineChart.axisLeft.setDrawLimitLinesBehindData(true)
            val limitLine = LimitLine(limit.toFloat(), "Target")
            limitLine.lineWidth = 3f
            limitLine.enableDashedLine(10f, 10f, 0f)
            limitLine.labelPosition = LimitLabelPosition.LEFT_TOP
            limitLine.textSize = 10f

            mBinding.lineChart.axisLeft.addLimitLine(limitLine)
        }
    }

    private fun showEnterTargetDialog() {
        val view = LayoutInflater.from(activity!!).inflate(R.layout.dialog_enter_target, null)
        val dialog = AlertDialog.Builder(activity!!).setView(view).create()

        val btnSave = view.findViewById<TextView>(R.id.btnSave)
        val btnCancel = view.findViewById<TextView>(R.id.btnCancel)
        val edtInput = view.findViewById<EditText>(R.id.edtTarget)

        edtInput.setText(mViewModel.targetWordsPerDay.value.toString())
        btnCancel.setOnClickListener { dialog.dismiss() }
        btnSave.setOnClickListener {
            val target = edtInput.text.toString()
            mViewModel.saveTargetWordPerDay(target.toInt())
            dialog.dismiss()
        }

        dialog.show()
        edtInput.requestFocus()
    }
}