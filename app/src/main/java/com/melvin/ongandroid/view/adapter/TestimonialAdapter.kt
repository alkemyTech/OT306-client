package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.TestimonialItemBinding
import com.melvin.ongandroid.model.testimonial.Testimonial

class TestimonialAdapter :
    ListAdapter<Testimonial, TestimonialAdapter.TestimonialViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestimonialViewHolder {
        return TestimonialViewHolder(
            TestimonialItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TestimonialViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }

    class TestimonialViewHolder(
        private var binding: TestimonialItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /*
        Bind Testimonial UI Layout Item with Testimonial element
         */
        fun bind(testimonial: Testimonial) {
            binding.apply {
                nombreYAp.text = testimonial.name ?: ""
                testimoniot.text = testimonial.description ?: ""
                testimonial.image?.let { imgUrl ->
                    Glide.with(testimonialImage.context)
                        .load(imgUrl)
                        .into(testimonialImage)
                }
            }
        }

    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Testimonial>() {
            override fun areItemsTheSame(oldItem: Testimonial, newItem: Testimonial): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Testimonial, newItem: Testimonial): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}