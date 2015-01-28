package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int memory =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void memoryHandle(View v)
    {
        int id = v.getId();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        switch(id){
            case R.id.madd:
                int memadd = Integer.parseInt(tvAns.getText().toString());
                memory+=memadd;
                Toast t1 = Toast.makeText(this.getApplicationContext(),
                        "Memory Added, Value="+memory, Toast.LENGTH_SHORT);
                t1.show();
                break;
            case R.id.msub:
                int memsub = Integer.parseInt(tvAns.getText().toString());
                memory-=memsub;
                Toast t2 = Toast.makeText(this.getApplicationContext(),
                        "Memory Subtracted, Value="+memory, Toast.LENGTH_SHORT);
                t2.show();
                break;
            case R.id.mr:
                TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
                tvAns.setText(Integer.toString(memory));
                Toast t3 = Toast.makeText(this.getApplicationContext(),
                        "Memory Showed", Toast.LENGTH_SHORT);
                expr = new StringBuffer(Integer.toString(memory));
                updateExprDisplay();
                t3.show();
                break;
            case R.id.mc:
                memory = 0;
                Toast t4 = Toast.makeText(this.getApplicationContext(),
                        "Memory Cleared, Value="+memory, Toast.LENGTH_SHORT);
                t4.show();
                break;
            default: break;
        }
    }

    public void recalculate() {
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        if(tokens.length == 1)
        {
            tvAns.setText(tokens[0]);
        }
        else
        {
            if(tokens.length%2 == 1)
            {
               int result = Integer.parseInt(tokens[0]);
               for(int i=1;i<tokens.length;i+=2)
               {
                   String operator = tokens[i];
                   int operand = Integer.parseInt(tokens[i+1]);
                   if(operator.equals("+"))
                   {
                     result+=operand;
                   }
                   if(operator.equals("-"))
                   {
                     result-=operand;
                   }
                   if(operator.equals("*"))
                   {
                     result*=operand;
                   }
                   if(operator.equals("/"))
                   {
                     result/=operand;
                   }
               }
               tvAns.setText(Integer.toString(result));
            }
        }
    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator and expr is not "",
        if(expr.length()!=0)
        {
            char last = expr.charAt(expr.length() - 1);
            if (last != '+' && last != '-' && last != '*' && last != '/')
            {
                //THEN append the clicked operator and updateExprDisplay,
                String d = ((TextView) v).getText().toString();
                expr.append(d);
                updateExprDisplay();
            }
        }
    }

    public void equalClicked(View v){
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        String result = tvAns.getText().toString();
        expr = new StringBuffer(result);
        updateExprDisplay();
        tvAns.setText("0");
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(expr.toString());
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            updateExprDisplay();
            recalculate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
