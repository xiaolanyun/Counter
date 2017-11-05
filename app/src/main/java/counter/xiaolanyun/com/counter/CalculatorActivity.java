package counter.xiaolanyun.com.counter;

import java.math.BigDecimal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;//一个弹出框，提示信息用！

//计算器
//思路:在java中要重新定义布局按钮，与activity_main中id进行联系
//对按钮进行绑定监听器，按了哪个按钮要进行识别
//十进制加减乘除计算
public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_show;
    private Button btnBack;
    private Button btnAC;
    private BigDecimal num1, num2;//大的小数处理

    private StringBuffer str_show = new StringBuffer("");//字符串变量
    private String str_operate = null;
    private String str_result = null;//无结果状态
    private int scale = 10;//控制小数位数

    private boolean flag_noDot = true;//没有.
    private boolean flag_haveNum1 = false;//有没有操作数1
    private boolean flag_haveText = false;//textVew是否为空

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    //按钮绑定监听器
    private void initView() {
        tv_show = (TextView) findViewById(R.id.tv_show);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        findViewById(R.id.btnDiv).setOnClickListener(this);
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnMul).setOnClickListener(this);
        findViewById(R.id.btnSub).setOnClickListener(this);

        findViewById(R.id.btnEqu).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        btnAC=(Button)findViewById(R.id.btnAC);
        btnAC.setOnClickListener(this);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
//长按退格键进行清零
        btnBack.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                tv_show.setText("");
                str_show = new StringBuffer("");
                flag_noDot = true;
                flag_haveNum1 = false;
                flag_haveText = false;
                str_operate = null;
                return true;
            }
        });
    }
//按钮点击事件
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        switch (v.getId()) {
            case R.id.btnAC:    tv_show.setText("");
                                str_show = new StringBuffer("");
                                flag_noDot = true;
                                flag_haveNum1 = false;
                                flag_haveText = false;
                                str_operate = null;
                                break;
            case R.id.btnBack:
                                if (str_show.toString() != "")
                                {
                                    if (!flag_noDot)
                                    {
                                        //String.valueOf（）意识说要参数给值转化String类型
                                        //charAt() 方法可返回指定位置的字符
                                        String lastStr = String.valueOf(str_show.charAt(str_show.length()-1));
                                        if (lastStr.equals("."))
                                        {
                                            flag_noDot = true;
                                        }
                                    }
                                    str_show.deleteCharAt(str_show.length() - 1);
                                    if(str_show.toString().equals(""))
                                    {
                                        flag_haveText = false;
                                    }
                                    showInTextView(str_show.toString());
                                }
                                else
                                {
                                    showInTextView("");
                                    str_result = null;
                                    str_show = new StringBuffer("");
                                    flag_noDot = true;
                                    flag_haveText = false;
                                }
                                flag_haveNum1 = false;
                                break;

            //实现各种运算
            case R.id.btnAdd:
                                setNum1(btn.getText().toString());
                                break;
            case R.id.btnSub:
                                if (!flag_haveText)
                                {
                                    if (str_show.toString().equals(""))
                                    {
                                        //负号
                                        str_show.append("-");
                                        showInTextView(str_show.toString());
                                        flag_haveText = true;
                                        break;
                                    }
                                }
                                setNum1(btn.getText().toString());
                                break;
            case R.id.btnMul:
                                setNum1(btn.getText().toString());
                                break;
            case R.id.btnDiv:
                                setNum1(btn.getText().toString());
                                break;
            case R.id.btnEqu:
                                if (str_operate == null || str_show.toString().equals("")
                                        || !flag_haveNum1)
                                    break;
                                calculate();
                                break;

            case R.id.btnDot:
                                if (str_show.toString() == "")
                                {
                                    break;
                                }
                                else if (flag_noDot)
                                {
                                    str_show.append(".");
                                    showInTextView(str_show.toString());
                                    flag_noDot = false;
                                }
                                break;
            default:
                                str_show.append(btn.getText().toString());
                                showInTextView(str_show.toString());
                                break;
        }
    }

    private void setNum1(String oper) {
        str_operate = oper;
        if (str_operate != null &&    !str_show.toString().equals("") && flag_haveNum1)
        {
            calculate();
        }
        if (str_show.toString() != "" &&   !str_show.toString().equals("-"))
        {
//除去一开始输入-的干扰，比如输入+*/
            num1 = new BigDecimal(str_show.toString());
            str_show = new StringBuffer("");
            showInTextView(str_show.toString());
            str_result = null;
            flag_haveNum1 = true;
            flag_haveText = false;
        }
        //结果不为空就可以继续计算
        else if (str_result != null) {
            num1 = new BigDecimal(str_result);
            showInTextView(str_result);
            str_result = null;
            flag_haveNum1 = true;
            flag_haveText = false;
        }
        flag_noDot = true;
    }

    private void calculate() {
        if(str_show.toString().equals("-")) return;
        double result = 0;
        num2 = new BigDecimal(str_show.toString());
        if (str_operate.equals("+"))
        {
            result = Calculate.add(num1, num2);
        }
        if (str_operate.equals("-"))
        {
            result = Calculate.sub(num1, num2);
        }
        if (str_operate.equals("*")) {
            result = Calculate.mul(num1, num2);
        }
        if (str_operate.equals("/"))
        {
            if (!num2.equals(BigDecimal.ZERO))
            {
                result = Calculate.div(num1, num2, scale);
            }
            else
            {
                Toast.makeText(CalculatorActivity.this, "0不能做除数", Toast.LENGTH_LONG)
                        .show();
                showInTextView("");
                str_show = new StringBuffer("");
                str_operate = null;
                flag_haveNum1 = false;
                flag_noDot = true;
                return;
            }
        }
        str_result = String.valueOf(Calculate.round(result, scale));
        //从指定边界处划分成字符串数组，比如10.0，输出10
        String[] resultStrings = str_result.split("\\.");
        if (resultStrings[1].equals("0"))
        {
            str_result = resultStrings[0];
        }
         showInTextView(str_result);
        str_show = new StringBuffer("");
        flag_noDot = true;
        flag_haveNum1 = false;
        str_operate = null;
        flag_haveText = true;
    }

    private void showInTextView(String str)
    {
        tv_show.setText(str);
    }

}
