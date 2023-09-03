package com.example.tazakhabar.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tazakhabar.APIsInterface.GetNewsApi
import com.example.tazakhabar.Utils.Constants.API_KEY
import com.example.tazakhabar.modelClasses.Article
import com.example.tazakhabar.modelClasses.TotalArticles
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel //Hilt will automatically provide any dependencies that your ViewModel needs.
// This saves you the trouble of manually passing in dependencies
class viewModel @Inject constructor(val getNewsApi: GetNewsApi) : ViewModel() {

    private lateinit var country: String
    private var pageSize: Int = 0
    var changeAbleList = MutableLiveData<List<Article>?>()
    var errorMessage = MutableLiveData<String>()
    var receviedNews = ArrayList<Article>()

    fun argumentSetter(country: String, pageSize: Int) {
        this.country = country
        this.pageSize = pageSize
        viewModelScope.launch {
            getNewsFromApi(country, pageSize, "")
        }
    }

    fun getNewsList(): MutableLiveData<List<Article>?> {
        return changeAbleList
    }

    fun searchNews(searchText: String) {
        receviedNews.clear()
        changeAbleList.value = null
        viewModelScope.launch {
            val response = getNewsApi.get_News_with_keywords(searchText, API_KEY)
            handleResponse(response)
        }
    }

    fun handleResponse(response: Response<TotalArticles>) {
        if (response.isSuccessful && response.body() != null) {
            receviedNews.addAll(response.body()!!.articles)
            changeAbleList.value = receviedNews
        } else if (response.errorBody() != null) {
            changeAbleList.value = null
            val parser = JsonParser()
            val errorDetails = parser.parse(response.errorBody()!!.charStream().readText()).asString
            errorMessage.value = errorDetails
        }
    }

    fun getCategory(category: String) {
        getNewsFromApi(country, pageSize, category)
    }

    fun getNewsFromApi(country: String, pageSize: Int, category: String) {
        receviedNews.clear()
        changeAbleList.value = null
        viewModelScope.launch {
            val response: Response<TotalArticles>
            if (category == "") {
                response = getNewsApi.get_General_News(country, pageSize, API_KEY)
            } else {
                response = getNewsApi.get_Category_Wise(country, category, pageSize, API_KEY)
            }
            handleResponse(response)
        }
    }
}