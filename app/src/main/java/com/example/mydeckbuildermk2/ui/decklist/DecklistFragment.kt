package com.example.mydeckbuildermk2.ui.decklist

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mydeckbuildermk2.databinding.FragmentDecklistBinding
import com.example.mydeckbuildermk2.databinding.FragmentDecklistCardBinding
import com.example.mydeckbuildermk2.ui.adapters.CardAdapter
import com.example.mydeckbuildermk2.ui.models.CardMemStore
import com.example.mydeckbuildermk2.ui.models.CardModel
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit


//import com.example.mydeckbuildermk2.databinding.FragmentDecklistBindingBinding



class DecklistFragment : Fragment() {

    //val quantity = 0
    //val type = "basic land"

    //  private val application: Any = TODO()

    // lateinit var decklistlayout : FragmentDecklistBinding
    private var _binding: FragmentDecklistBinding? = null
    private val binding get() = _binding!!

    lateinit var cardMemStore: CardMemStore
    var cardNum = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val decklistViewModel =
        //   ViewModelProvider(this).get(DecklistViewModel::class.java)
        _binding = FragmentDecklistBinding.inflate(inflater, container, false)
        /*  binding.amountPicker.minValue = 1
        binding.amountPicker.maxValue = 1000

        binding.amountPicker.setOnValueChangedListener { _, _, newVal
            //Display the newly selected number to paymentAmount
            binding.amount.setText("$newVal")->*/



        cardMemStore = CardMemStore()
        //cardMemStore.create(CardModel(name = "hello"))

        // binding = FragmentDecklistBinding.inflate(layoutInflater)
        //val app: MyDeckbuilder = this.application as MyDeckbuilder


        _binding = FragmentDecklistBinding.inflate(inflater, container, false)
        binding.list.layoutManager
        binding.list.adapter = CardAdapter(cardMemStore.findAll())
        val root: View = binding.root
        setButtonListener(binding)
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cardNum=binding.amount.toString().toInt()

            }
        })
        // val textView: TextView = binding.textGallery
        // decklistViewModel.text.observe(viewLifecycleOwner) {
        //textView.text = it
        //  }


        return root
    }

    /*fun setButtonListener(layout: FragmentDecklistCardBinding) {
        layout.add.setOnClickListener()
        {
        }

    }

    fun setButtonListener(layout: FragmentDecklistCardBinding) {
        layout.delete.setOnClickListener()
        {
        }

    }*/

    fun setButtonListener(layout: FragmentDecklistBinding) {
        layout.ok.setOnClickListener {
            val searchedCard = if (layout.search.text.isNotEmpty())
                layout.search.text.toString() else layout.search.toString()
                   search(searchedCard)
            (binding.list.adapter)?.notifyItemRangeChanged(0, cardMemStore.cards.size)



            // if (quantity > 4 && type.equals("basic land")
        }
    }

   /* fun setButtonListener(layout: FragmentDonateBinding) {
        layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = getString(R.string.totalSoFar,totalDonated)
                layout.progressBar.progress = totalDonated
                app.donationsStore.create(DonationModel(paymentmethod = paymentmethod,amount = amount))
            }
        }
    }*/




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun search(name: String): String? {
        // val foundCard: CardModel? = cards.find { it.name.equals(name)}
        val jsonStr = " https://api.scryfall.com/cards/named?fuzzy=" + name
        try {

            JsonTask().execute(jsonStr)

        } catch (ex: JSONException) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex)
        }

        return jsonStr
    }



    private inner class JsonTask :
        AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): String? {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val buffer = StringBuffer()
            var line = ""
            try {
                val url = URL(p0[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val stream: InputStream = connection.getInputStream()
                reader = BufferedReader(InputStreamReader(stream))
                var allText = stream.bufferedReader().use(BufferedReader::readText)
                allText = """$allText""".trimIndent()

                Log.d("Response: ", "> $allText") //here u ll get whole response...... :-)


                return allText
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (connection != null) {
                    connection.disconnect()
                }
                try {
                    if (reader != null) {
                        reader.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.e("reponse", result.toString())

            val json = JSONObject(result.toString())
            val cardNameString = json.getString("name")
            val cardTypeString = json.getString("type_line")
            //binding.name.text= cardNameString
            var cardObject = CardModel(name = cardNameString, type = cardTypeString, quantity = cardNum )
            //add the object tp yhe list
            cardMemStore.create(cardObject)

        }


    }
}