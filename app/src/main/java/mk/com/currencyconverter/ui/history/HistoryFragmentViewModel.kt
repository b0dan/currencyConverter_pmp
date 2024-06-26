package mk.com.currencyconverter.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryFragmentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the History fragment"
    }
    val text: LiveData<String> = _text
}