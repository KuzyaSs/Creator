package ru.ermakov.creator.presentation.screen.balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentBalanceBinding
import ru.ermakov.creator.presentation.adapter.UserTransactionAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject


private const val THRESHOLD = 5

class BalanceFragment : Fragment() {
    private var _binding: FragmentBalanceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var balanceViewModelFactory: BalanceViewModelFactory
    private lateinit var balanceViewModel: BalanceViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var userTransactionAdapter: UserTransactionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        balanceViewModel = ViewModelProvider(
            this,
            balanceViewModelFactory
        )[BalanceViewModel::class.java]
        setUpSwipeRefreshLayout()
        setUpTransactionRecyclerView()
        setUpListeners()
        setUpObservers()
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

    private fun setUpTransactionRecyclerView() {
        userTransactionAdapter = UserTransactionAdapter(
            textLocalizer = textLocalizer
        ) { userTransactionItem -> navigateToBlogFragment(creatorId = userTransactionItem.user.id) }
        binding.recyclerViewTransactions.adapter = userTransactionAdapter
        binding.recyclerViewTransactions.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != SCROLL_STATE_IDLE) {
                    balanceViewModel.loadNextUserTransactionPage()
                }
            }
        })
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                balanceViewModel.refreshUserTransactions()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewTransactions.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener {
                goBack()
            }
            viewLoading.setOnClickListener { }
            cardViewTitle.setOnLongClickListener {
                balanceViewModel.swapHeaderVisibility()
                true
            }
        }
    }

    private fun setUpObservers() {
        balanceViewModel.balanceUiState.observe(viewLifecycleOwner) { balanceUiState ->
            balanceUiState.apply {
                if (userTransactionItems != null) {
                    binding.textViewBalance.text = balance.toString()
                    userTransactionAdapter?.submitList(userTransactionItems)
                    binding.constraintLayoutHeader.isVisible = isHeaderShown
                    setEmptyUserTransactionsInfo(isUserTransactionsEmpty = userTransactionItems.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = userTransactionItems == null
                binding.progressBarScreen.isVisible = isLoadingShown && userTransactionItems == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && userTransactionItems == null
                }
                binding.imageViewScreenLogo.isVisible =
                    isErrorMessageShown && userTransactionItems == null
            }
        }
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = BalanceFragmentDirections.actionBalanceFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun setEmptyUserTransactionsInfo(isUserTransactionsEmpty: Boolean) {
        binding.imageViewHistory.isVisible = isUserTransactionsEmpty
        binding.textViewEmptyListMessage.isVisible = isUserTransactionsEmpty
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            balanceViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}