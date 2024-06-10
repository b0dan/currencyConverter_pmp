package mk.com.currencyconverter.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import mk.com.currencyconverter.R
import mk.com.currencyconverter.adapter.HistoryAdapter
import mk.com.currencyconverter.databinding.FragmentHistoryBinding
import mk.com.currencyconverter.db.History
import mk.com.currencyconverter.db.HistoryDatabase
import mk.com.currencyconverter.db.HistoryRepository

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var dataList: ArrayList<History>
    private lateinit var historyAdapter: HistoryAdapter
    lateinit var input: Array<String>
    lateinit var result: Array<String>
    private val database by lazy { HistoryDatabase.getDatabase(requireContext()) }
    private val repository by lazy { HistoryRepository(database.historyDao()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyFragmentViewModel =
            ViewModelProvider(this).get(HistoryFragmentViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        historyFragmentViewModel.text.observe(viewLifecycleOwner) {

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHistoryBinding.bind(view)

        dataList = ArrayList()
        historyAdapter = HistoryAdapter(dataList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = historyAdapter

        lifecycleScope.launch {
            repository.allHistory.collect { historyList ->
                dataList.clear()
                dataList.addAll(historyList)
                historyAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}