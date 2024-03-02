package ru.ermakov.creator.presentation.screen.createPost

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.databinding.FragmentSelectSubscriptionsBinding
import ru.ermakov.creator.presentation.adapter.ChooseSubscriptionAdapter

class SelectSubscriptionsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectSubscriptionsBinding? = null
    private val binding get() = _binding!!

    private var chooseSubscriptionAdapter: ChooseSubscriptionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectSubscriptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSelectSubscriptionsScreen()
        setUpListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setUpSelectSubscriptionsScreen()
    }

    private fun setUpSelectSubscriptionsScreen() {
        val subscriptions = (requireParentFragment() as SubscriptionSelectorSource)
            .getSubscriptions()
        val requiredSubscriptionIds = (requireParentFragment() as SubscriptionSelectorSource)
            .getRequiredSubscriptionIds()
        setUpSubscriptionRecyclerView(selectedSubscriptionIds = requiredSubscriptionIds)
        chooseSubscriptionAdapter?.submitList(subscriptions)
        binding.linearLayoutRecyclerViewCategoriesState.isVisible = subscriptions.isNullOrEmpty()
    }

    private fun setUpSubscriptionRecyclerView(selectedSubscriptionIds: List<Long>) {
        chooseSubscriptionAdapter = ChooseSubscriptionAdapter(
            selectedSubscriptionIds = selectedSubscriptionIds.toMutableList(),
            onItemClickListener = {
                (requireParentFragment() as SubscriptionSelectorSource).changeSelectedSubscriptionIds(
                    selectedSubscriptionIds = chooseSubscriptionAdapter
                        ?.getSelectedSubscriptionIds() ?: listOf()
                )
            }
        )
        binding.recyclerViewCategories.adapter = chooseSubscriptionAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }
            buttonManage.setOnClickListener {
                (requireParentFragment() as SubscriptionSelectorSource).navigateToSubscriptionsFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}