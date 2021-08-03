import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.policy.R

class MyAdapter(@get:JvmName("getAdapterContext") var context: Context,
                var rCompanies: Array<String>, var rSumInsured: Array<String>,
                var rPremium: Array<String>) : ArrayAdapter<String>(context,  R.layout.row, R.id.textview1, rCompanies)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = layoutInflater.inflate(R.layout.row, parent, false)
        val myCompany = row.findViewById<TextView>(R.id.textview1)
        val mySI = row.findViewById<TextView>(R.id.textview2)
        val myPremium = row.findViewById<TextView>(R.id.textview3)

        myCompany.setText(rCompanies.get(position))
        mySI.setText(rSumInsured.get(position))
        myPremium.setText(rPremium.get(position))
        return row
    }
}