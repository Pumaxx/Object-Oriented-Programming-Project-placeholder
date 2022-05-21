package com.example.timersinwindows

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timer_content.view.*

class TimerAdapter(private val timersList: MutableList<Timers>
) : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.timer_content,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        lateinit var countdownTimer: CountDownTimer

        val currentTimer = timersList[position]
        holder.itemView.apply {
            tvTime.text = currentTimer.getTimeString()
            currentTimer.setupTimer(10000L)

            btStart.setOnClickListener {
                tvTime.text = currentTimer.getTimeString()

                if (!currentTimer.getIsRunning()) {
                    countdownTimer = object : CountDownTimer(currentTimer.timeInMilliSeconds, 1000) {
                            override fun onFinish() {
                                currentTimer.setupTimer(0L)
                                tvTime.text = currentTimer.getTimeString()
                                currentTimer.setIsRunning(false)
                            }

                            override fun onTick(p0: Long) {
                                currentTimer.setupTimer(p0)
                                tvTime.text = currentTimer.getTimeString()
                            }
                        }
                    currentTimer.setIsRunning(true)
                    countdownTimer.start()
                }
            }

            btPause.setOnClickListener {
                if (currentTimer.getIsRunning())
                    countdownTimer.cancel()
                currentTimer.setIsRunning(false)
            }

            btEdit.setOnClickListener {
                btStart.visibility = View.INVISIBLE
                btPause.visibility = View.INVISIBLE
                tvTime.visibility = View.INVISIBLE
                btDelete.visibility = View.INVISIBLE
                btSet.visibility = View.VISIBLE
                etTimeToSet.visibility = View.VISIBLE
                btEdit.visibility = View.INVISIBLE
            }

            btSet.setOnClickListener {
                if (etTimeToSet.text.toString().isEmpty())
                    currentTimer.setupTimer(10 * 1000L)
                else
                    currentTimer.setupTimer(etTimeToSet.text.toString().toLong() * 1000L)

                tvTime.text = currentTimer.getTimeString()

                btStart.visibility = View.VISIBLE
                btPause.visibility = View.VISIBLE
                tvTime.visibility = View.VISIBLE
                btEdit.visibility = View.VISIBLE
                btDelete.visibility = View.VISIBLE
                etTimeToSet.visibility = View.INVISIBLE
                btSet.visibility = View.INVISIBLE
            }

            btDelete.setOnClickListener {
                timersList.remove(currentTimer)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return timersList.size
    }

    fun addTimer(newTimer: Timers) {
        timersList.add(newTimer)
        notifyItemInserted(timersList.size - 1)
    }
}