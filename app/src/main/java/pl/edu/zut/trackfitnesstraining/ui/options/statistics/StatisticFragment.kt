package pl.edu.zut.trackfitnesstraining.ui.options.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.LineGraphSeries
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.zut.trackfitnesstraining.R
import pl.edu.zut.trackfitnesstraining.databinding.FragmentStatisticBinding
import pl.edu.zut.trackfitnesstraining.util.UiResState
import pl.edu.zut.trackfitnesstraining.util.hide
import pl.edu.zut.trackfitnesstraining.util.show
import java.util.*

@AndroidEntryPoint
class StatisticFragment : Fragment() {
    private val TAG = "StatisticFragment"
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private lateinit var statisticViewModel: StatisticViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        statisticViewModel = ViewModelProvider(this)[StatisticViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val allMonths = arrayOf(
            getString(R.string.january),
            getString(R.string.february),
            getString(R.string.march),
            getString(R.string.april),
            getString(R.string.may),
            getString(R.string.june),
            getString(R.string.july),
            getString(R.string.august),
            getString(R.string.september),
            getString(R.string.october),
            getString(R.string.november),
            getString(R.string.december)
        )
        val months = mutableListOf<String>()
        for (i in 1..12) {
            months.add(allMonths[(currentMonth - i + 13) % 12])
        }

        val graphViewWeight = binding.idGraphView
        val graphViewKcal = binding.idGraphViewKcal
        val graphViewMeasurment = binding.idGraphViewMeasurment
        val graphViewWorkout = binding.idGraphViewWorkout

        val spinnerMonth = binding.spinnerMonth

        val adapterMonth = context?.let {
            ArrayAdapter(
                it,
                androidx.transition.R.layout.support_simple_spinner_dropdown_item, months
            )
        }
        spinnerMonth.adapter = adapterMonth
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedNum = position
                val whichMonth = (currentMonth - selectedNum + 13) % 12
                statisticViewModel.getUserWeight()
                statisticViewModel.userWeight.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is UiResState.Loading -> {
                            Log.e(TAG, "Loading")
                        }
                        is UiResState.Failure -> {
                            Log.e(TAG, state.error.toString())
                        }
                        is UiResState.Succes -> {
                            if (state.data.isEmpty())
                            else {
                                val weights = statisticViewModel.graphDataWeight(whichMonth)
                                if (weights.isEmpty()) {
                                    graphViewWeight.hide()
                                    binding.textDay.text = getString(R.string.brakDanych)
                                } else {
                                    graphViewWeight.show()
                                    binding.textDay.text = getString(R.string.dzie_miesiaca)
                                }
                                val series = LineGraphSeries(weights)
                                series.thickness = 15
                                series.isDrawDataPoints = true
                                series.dataPointsRadius = 20F
                                series.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.waga)} = $y kg\n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                graphViewWeight.removeAllSeries()
                                graphViewWeight.addSeries(series)
                                graphViewWeight.gridLabelRenderer.textSize = 60f
                                graphViewWeight.gridLabelRenderer.verticalAxisTitle =
                                    getString(R.string.waga_kg)
                                graphViewWeight.gridLabelRenderer.verticalAxisTitleTextSize = 60f
                                graphViewWeight.viewport.isScalable = true
                                graphViewWeight.viewport.setScalableY(true)
                                graphViewWeight.viewport.isScrollable = true
                                graphViewWeight.viewport.setMinX(0.0)
                                graphViewWeight.viewport.setMaxX(33.0)
                            }

                        }
                    }
                }
                statisticViewModel.getWorkout()
                statisticViewModel.workout.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is UiResState.Loading -> {
                            Log.e(TAG, "Loading")
                        }
                        is UiResState.Failure -> {
                            Log.e(TAG, state.error.toString())
                        }
                        is UiResState.Succes -> {
                            if (state.data.isEmpty())
                            else {
                                val workoutsNum = statisticViewModel.graphDataWorkout(whichMonth)
                                val kcal = statisticViewModel.graphDataKcal(whichMonth)
                                if (kcal.isEmpty()) {
                                    graphViewKcal.hide()
                                    binding.textDayKcal.text = getString(R.string.brakDanych)
                                } else {
                                    graphViewKcal.show()
                                    binding.textDayKcal.text = getString(R.string.dzie_miesiaca)
                                }
                                if (workoutsNum.isEmpty()) {
                                    graphViewWorkout.hide()
                                    binding.textDayWorkout.text = getString(R.string.brakDanych)
                                } else {
                                    graphViewWorkout.show()
                                    binding.textDayWorkout.text = getString(R.string.dzie_miesiaca)
                                }
                                val seriesWorkout = BarGraphSeries(workoutsNum)
                                seriesWorkout.spacing = 60
                                seriesWorkout.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.ilosc)} = $y \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                val series = BarGraphSeries(kcal)
                                series.spacing = 60
                                series.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "kcal = $y\n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                series.dataWidth = 3.0
                                series.color = Color.BLUE
                                graphViewKcal.removeAllSeries()
                                graphViewKcal.addSeries(series)
                                graphViewKcal.gridLabelRenderer.labelVerticalWidth = 150
                                graphViewKcal.gridLabelRenderer.textSize = 60f
                                graphViewKcal.gridLabelRenderer.verticalAxisTitle =
                                    getString(R.string.spalonekcal)
                                graphViewKcal.gridLabelRenderer.verticalAxisTitleTextSize = 60f
                                graphViewKcal.viewport.isScalable = true
                                graphViewKcal.viewport.setScalableY(true)
                                graphViewKcal.gridLabelRenderer.numVerticalLabels = 3
                                graphViewKcal.viewport.isScrollable = true
                                graphViewKcal.viewport.setMinX(0.0)
                                graphViewKcal.viewport.setMaxX(33.0)


                                seriesWorkout.dataWidth = 3.0
                                seriesWorkout.color = Color.BLUE
                                graphViewWorkout.removeAllSeries()
                                graphViewWorkout.addSeries(seriesWorkout)
                                graphViewWorkout.gridLabelRenderer.labelVerticalWidth = 100
                                graphViewWorkout.gridLabelRenderer.textSize = 60f
                                graphViewWorkout.gridLabelRenderer.verticalAxisTitle =
                                    getString(R.string.liczbaTren)
                                graphViewWorkout.gridLabelRenderer.verticalAxisTitleTextSize = 60f
                                graphViewWorkout.viewport.isScalable = true
                                graphViewWorkout.viewport.setScalableY(true)
                                graphViewWorkout.gridLabelRenderer.numVerticalLabels = 3
                                graphViewWorkout.viewport.isScrollable = true
                                graphViewWorkout.viewport.setMinX(1.0)
                                graphViewWorkout.viewport.setMinY(0.0)
                                graphViewWorkout.viewport.setMaxX(33.0)
                            }
                        }
                    }
                }
                statisticViewModel.getUserMeasurment()
                statisticViewModel.userMeasurment.observe(viewLifecycleOwner) { state ->
                    when (state) {
                        is UiResState.Loading -> {
                            Log.e(TAG, "Loading")
                        }
                        is UiResState.Failure -> {
                            Log.e(TAG, state.error.toString())
                        }
                        is UiResState.Succes -> {
                            if (state.data.isEmpty())
                            else {
                                val measurmentList =
                                    statisticViewModel.graphDataMeasurement(whichMonth)
                                val seriesNeck = LineGraphSeries(measurmentList[0])
                                val seriesChest = LineGraphSeries(measurmentList[1])
                                val seriesBiceps = LineGraphSeries(measurmentList[2])
                                val seriesWeist = LineGraphSeries(measurmentList[3])
                                val seriesStomach = LineGraphSeries(measurmentList[4])
                                val seriesHip = LineGraphSeries(measurmentList[5])
                                val seriesTigh = LineGraphSeries(measurmentList[6])
                                val seriesCalv = LineGraphSeries(measurmentList[7])

                                seriesNeck.color = Color.BLUE
                                seriesChest.color = Color.GREEN
                                seriesBiceps.color = Color.RED
                                seriesWeist.color = Color.YELLOW
                                seriesStomach.color = Color.CYAN
                                seriesHip.color = Color.MAGENTA
                                seriesTigh.color = Color.BLACK
                                seriesCalv.color = Color.GRAY

                                seriesNeck.thickness = 12
                                seriesChest.thickness = 12
                                seriesBiceps.thickness = 12
                                seriesWeist.thickness = 12
                                seriesStomach.thickness = 12
                                seriesHip.thickness = 12
                                seriesTigh.thickness = 12
                                seriesCalv.thickness = 12

                                seriesNeck.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.neck)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                seriesChest.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.chest)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesBiceps.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.biceps)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesWeist.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.weist)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesStomach.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.stomach)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesHip.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.hips)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesTigh.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.tigh)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                seriesCalv.setOnDataPointTapListener { _, dataPoint ->
                                    val y = dataPoint.y.toInt()
                                    val x = dataPoint.x.toInt()
                                    Toast.makeText(
                                        context,
                                        "${getString(R.string.calv)} = $y cm \n${getString(R.string.dzien)} = $x ${spinnerMonth.selectedItem}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                                seriesNeck.isDrawDataPoints = true
                                seriesChest.isDrawDataPoints = true
                                seriesBiceps.isDrawDataPoints = true
                                seriesWeist.isDrawDataPoints = true
                                seriesStomach.isDrawDataPoints = true
                                seriesHip.isDrawDataPoints = true
                                seriesTigh.isDrawDataPoints = true
                                seriesCalv.isDrawDataPoints = true

                                seriesNeck.title = getString(R.string.neck)
                                seriesChest.title = getString(R.string.chest)
                                seriesBiceps.title = getString(R.string.biceps)
                                seriesWeist.title = getString(R.string.weist)
                                seriesStomach.title = getString(R.string.stomach)
                                seriesHip.title = getString(R.string.hips)
                                seriesTigh.title = getString(R.string.tigh)
                                seriesCalv.title = getString(R.string.calv)

                                if (measurmentList[0].isEmpty()) {
                                    graphViewMeasurment.hide()
                                    binding.layoutLegend.hide()
                                    binding.textDayMeasurment.text =
                                        getString(R.string.brakDanychPomiar)
                                } else {
                                    graphViewMeasurment.show()
                                    binding.layoutLegend.show()
                                    binding.textDayMeasurment.text =
                                        getString(R.string.dzie_miesiaca)
                                }
                                graphViewMeasurment.removeAllSeries()
                                graphViewMeasurment.addSeries(seriesNeck)
                                graphViewMeasurment.addSeries(seriesChest)
                                graphViewMeasurment.addSeries(seriesBiceps)
                                graphViewMeasurment.addSeries(seriesWeist)
                                graphViewMeasurment.addSeries(seriesStomach)
                                graphViewMeasurment.addSeries(seriesHip)
                                graphViewMeasurment.addSeries(seriesTigh)
                                graphViewMeasurment.addSeries(seriesCalv)
                                graphViewMeasurment.gridLabelRenderer.textSize = 60f
                                graphViewMeasurment.gridLabelRenderer.verticalAxisTitle =
                                    getString(R.string.wymiaryCm)
                                graphViewMeasurment.gridLabelRenderer.verticalAxisTitleTextSize =
                                    60f
                                graphViewMeasurment.viewport.isScalable = true
                                graphViewMeasurment.viewport.setScalableY(true)
                                graphViewMeasurment.viewport.isScrollable = true
                                graphViewMeasurment.viewport.setMinX(0.0)
                                graphViewMeasurment.viewport.setMaxX(33.0)
                                graphViewMeasurment.gridLabelRenderer.numVerticalLabels = 12
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


