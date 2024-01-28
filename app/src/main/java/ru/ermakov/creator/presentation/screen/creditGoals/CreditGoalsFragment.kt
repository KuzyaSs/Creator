package ru.ermakov.creator.presentation.screen.creditGoals

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogCloseCreditGoalBinding
import ru.ermakov.creator.databinding.FragmentCreditGoalsBinding
import ru.ermakov.creator.presentation.adapter.CreditGoalAdapter
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreditGoalsFragment : Fragment(), OptionsHandler {
    private val arguments: CreditGoalsFragmentArgs by navArgs()
    private var _binding: FragmentCreditGoalsBinding? = null
    private val binding get() = _binding!!
    private var _dialogCloseCreditGoalBinding: DialogCloseCreditGoalBinding? = null

    @Inject
    lateinit var creditGoalsViewModelFactory: CreditGoalsViewModelFactory
    private lateinit var creditGoalsViewModel: CreditGoalsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var creditGoalAdapter: CreditGoalAdapter? = null
    private var closeCreditGoalDialog: Dialog? = null
    private var optionsFragment: OptionsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        creditGoalsViewModel = ViewModelProvider(
            this,
            creditGoalsViewModelFactory
        )[CreditGoalsViewModel::class.java]
        if (creditGoalsViewModel.creditGoalsUiState.value?.creditGoals == null) {
            creditGoalsViewModel.setCreditGoals(creatorId = arguments.creatorId)
        }
        optionsFragment = OptionsFragment(isEditShown = true, isCloseShown = true)
        setUpSwipeRefreshLayout()
        setCloseCreditGoalDialog()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        if (creditGoalsViewModel.creditGoalsUiState.value?.creditGoals != null) {
            creditGoalsViewModel.refreshCreditGoals(creatorId = arguments.creatorId)
        }
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.backgroundColor
                )
            )
            setColorSchemeColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        }
    }

    private fun setCloseCreditGoalDialog() {
        _dialogCloseCreditGoalBinding = DialogCloseCreditGoalBinding.inflate(layoutInflater)
        closeCreditGoalDialog = Dialog(requireContext())
        closeCreditGoalDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(_dialogCloseCreditGoalBinding?.root!!)
            setCancelable(false)
            _dialogCloseCreditGoalBinding?.textViewNo?.setOnClickListener {
                closeCreditGoalDialog?.dismiss()
            }
            _dialogCloseCreditGoalBinding?.textViewYes?.setOnClickListener {
                creditGoalsViewModel.closeSelectedCreditGoal(creatorId = arguments.creatorId)
                closeCreditGoalDialog?.dismiss()
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                creditGoalsViewModel.refreshCreditGoals(creatorId = arguments.creatorId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) ||
                        recyclerViewCreditGoals.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { navigateToCreateCreditGoalFragment() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        creditGoalsViewModel.creditGoalsUiState.observe(viewLifecycleOwner) { creditGoalsUiState ->
            creditGoalsUiState.apply {
                if (creditGoals != null) {
                    val isOwner = currentUserId == arguments.creatorId
                    binding.buttonCreate.isVisible = isOwner
                    setUpCreditGoalRecyclerView(isOwner = isOwner)
                    creditGoalAdapter?.submitList(creditGoals)
                    setEmptyListInfo(isEmptyList = creditGoals.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = creditGoals == null
                binding.progressBarScreen.isVisible = isLoadingShown && creditGoals == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && creditGoals == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && creditGoals == null
            }
        }
    }

    private fun setUpCreditGoalRecyclerView(isOwner: Boolean) {
        creditGoalAdapter = CreditGoalAdapter(
            isOwner = isOwner,
            onImageViewMoreClickListener = { creditGoal ->
                creditGoalsViewModel.setSelectedCreditGoal(creditGoal = creditGoal)
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            },
            onButtonDonateClickListener = { creditGoal ->
                navigateToDonateToCreditGoalFragment(creditGoalId = creditGoal.id)
            })
        binding.recyclerViewCreditGoals.adapter = creditGoalAdapter
    }

    private fun navigateToCreateCreditGoalFragment() {
        val action =
            CreditGoalsFragmentDirections.actionCreditGoalsFragmentToCreateCreditGoalFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDonateToCreditGoalFragment(creditGoalId: Long) {
        val action =
            CreditGoalsFragmentDirections.actionCreditGoalsFragmentToDonateToCreditGoalFragment(
                creatorId = arguments.creatorId,
                creditGoalId = creditGoalId
            )
        findNavController().navigate(action)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setEmptyListInfo(isEmptyList: Boolean) {
        binding.imageViewLogo.isVisible = isEmptyList
        binding.textViewEmptyList.isVisible = isEmptyList
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            creditGoalsViewModel.clearErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun edit() {
        Toast.makeText(
            requireContext(),
            "edit",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun delete() {}

    override fun close() {
        closeCreditGoalDialog?.show()
    }
}