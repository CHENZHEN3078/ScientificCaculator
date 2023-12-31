package com.example.caculator02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
bug：括号没用
*/

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private TextView text;
    private StringBuilder str = new StringBuilder();
    private double indexYN = 0;

    private double ans=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        editText = findViewById(R.id.editView);
        text = findViewById(R.id.textView);
        findViewById(R.id.btn_history).setOnClickListener(this);

    }

    public static String extractStrValue(String input) {
        String regex = "str=([^,}]+)"; // 正则表达式用于匹配 "str=" 后面的数字或小数
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // 返回匹配到的第一个分组
        }
        return "0"; // 如果未找到匹配，则返回 null
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            // 设置启动标志，避免多次返回同一页面的
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void clickButton(View view) {
        Button button = (Button) view;
        editText.append(button.getText());
        str.append(button.getText());
    }
    public void ans(View view) {
        editText.setText(null);
        str.setLength(0);
        str= new StringBuilder(extractStrValue(HistoryActivity.memory)) ;
        editText.append(str);
        //str.append(str);
        //Log.d("ning", ""+str);
    }
    public void empty(View view) {
        editText.setText(null);
        str.setLength(0);
    }

    public void delete(View view) {
        String nowText = editText.getText().toString();
        if (!nowText.isEmpty() && str.length()!=0) {
            editText.setText(nowText.substring(0, nowText.length() - 1));
            str.deleteCharAt(str.length() - 1);
        }
    }

    public void equal(View view) {
        indexYN = 0;
        estimate();
        if (indexYN == 0) {
            List<String> zhongZhui = zhuanZhongZhui(str.toString());
            List<String> houZhui = zhuanHouZhui(zhongZhui);

            ans=math(houZhui);
            editText.append("\n" + ans);

            //存str和ans
            ContentValues values = new ContentValues();
            values.put(EquationInfoContent.EQUATION_STR, String.valueOf(str));
            values.put(EquationInfoContent.EQUATION_ANS,ans);

            getContentResolver().insert(EquationInfoContent.CONTENT_URI, values);

            str.setLength(0);
            str.append(ans);

        }
    }

    public void reciprocal(View view) {
        editText.append("1/");
        str.append("1/");
    }

    public void factorial(View view) {
        editText.append("!");
        str.append("!");
    }

    public void power(View view) {
        editText.append("^");
        str.append("^");
    }

    public void squareRoot(View view) {
        editText.append("√");
        str.append("g");
    }

    public void eulerNumber(View view) {
        editText.append("e");
        str.append("e");
    }

    public void pi(View view) {
        editText.append("π");
        str.append("p");
    }

    public void sin(View view) {
        editText.append("sin");
        str.append("s");
    }

    public void cos(View view) {
        editText.append("cos");
        str.append("c");
    }

    public void tan(View view) {
        editText.append("tan");
        str.append("t");
    }

    /*public void ln(View view) {
        editText.append("ln");
        str.append("l");
    }
*/
    public void log(View view) {
        editText.append("log");
        str.append("o");
    }

    private List<String> zhuanZhongZhui(String str) {
        int index = 0;
        List<String> list = new ArrayList<>();
        do {
            char ch = str.charAt(index);
            if ("+-*/^!logsct()".indexOf(ch) >= 0) {
                index++;
                list.add(ch + "");
            } else if (ch == 'e' || ch == 'p') {
                index++;
                list.add(ch + "");
            } else if ("0123456789".indexOf(ch) >= 0) {
                String str1 = "";
                while (index < str.length() && "0123456789.".indexOf(str.charAt(index)) >= 0) {
                    str1 += str.charAt(index);
                    index++;
                }
                list.add(str1);
            }
        } while (index < str.length());
        return list;
    }

    public List<String> zhuanHouZhui(List<String> list) {
        Stack<String> fuZhan = new Stack<>();
        List<String> list2 = new ArrayList<>();
        if (!list.isEmpty()) {
            for (String item : list) {
                if (isNumber(item)) {
                    list2.add(item);
                } else if (isOperator(item) && item.charAt(0) != '(') {
                    if (fuZhan.isEmpty()) {
                        fuZhan.push(item);
                    } else {
                        if (item.charAt(0) != ')') {
                            if (adv(fuZhan.peek()) <= adv(item)) {
                                fuZhan.push(item);
                            } else {
                                while (!fuZhan.isEmpty() && !("(".equals(fuZhan.peek()))) {
                                    if (adv(item) <= adv(fuZhan.peek())) {
                                        list2.add(fuZhan.pop());
                                    }
                                }
                                if (fuZhan.isEmpty() || fuZhan.peek().charAt(0) == '(') {
                                    fuZhan.push(item);
                                }
                            }
                        } else if (item.charAt(0) == ')') {
                            while (fuZhan.peek().charAt(0) != '(') {
                                list2.add(fuZhan.pop());
                            }
                            fuZhan.pop();
                        }
                    }
                }
            }
            while (!fuZhan.isEmpty()) {
                list2.add(fuZhan.pop());
            }
        } else {
            editText.setText("");
        }
        return list2;
    }

    public static boolean isOperator(String op) {
        return "0123456789.ep".indexOf(op.charAt(0)) == -1;
    }

    public static boolean isNumber(String num) {
        return "0123456789ep".indexOf(num.charAt(0)) >= 0;
    }

    public static int adv(String f) {
        int result = 0;
        switch (f) {
            case "+":
            case "-":
                result = 1;
                break;
            case "*":
            case "/":
                result = 2;
                break;
            case "^":
            case "!":
            case "g":
            case "l":
            case "o":
            case "s":
            case "c":
            case "t":
                result = 3;
                break;
        }
        return result;
    }

    public double math(List<String> list2) {
        Stack<String> stack = new Stack<>();
        for (String item : list2) {
            if (isNumber(item)) {
                if (item.charAt(0) == 'e') {
                    stack.push(String.valueOf(Math.E));
                } else if (item.charAt(0) == 'p') {
                    stack.push(String.valueOf(Math.PI));
                } else {
                    stack.push(item);
                }
            } else if (isOperator(item)) {
                double res = 0;
                if (item.equals("+")) {
                    double num2 = Double.parseDouble(stack.pop());
                    double num1 = Double.parseDouble(stack.pop());
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    double num2 = Double.parseDouble(stack.pop());
                    double num1 = Double.parseDouble(stack.pop());
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    double num2 = Double.parseDouble(stack.pop());
                    double num1 = Double.parseDouble(stack.pop());
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    double num2 = Double.parseDouble(stack.pop());
                    double num1 = Double.parseDouble(stack.pop());
                    if (num2 != 0) {
                        res = num1 / num2;
                    } else {
                        editText.setText("Divider cannot be 0");
                        indexYN = 1;
                    }
                } else if (item.equals("^")) {
                    double num2 = Double.parseDouble(stack.pop());
                    double num1 = Double.parseDouble(stack.pop());
                    res = Math.pow(num1, num2);
                } else if (item.equals("!")) {
                    double num1 = Double.parseDouble(stack.pop());
                    if (num1 == 0 || num1 == 1) {
                        res = 1;
                    } else if (num1 == (int) num1 && num1 > 1) {
                        int d = 1;
                        for (int j = (int) num1; j > 0; j--) {
                            d *= j;
                        }
                        res = d;
                    } else {
                        editText.setText("Factorial must be a natural number");
                        indexYN = 1;
                    }
                } else if (item.equals("g")) {
                    double num1 = Double.parseDouble(stack.pop());
                    res = Math.sqrt(num1);
                } else if (item.equals("l")) {
                    double num1 = Double.parseDouble(stack.pop());
                    if (num1 > 0) {
                        res = Math.log(num1);
                    } else {
                        editText.setText("must be greater than 0");
                        indexYN = 1;
                    }
                } else if (item.equals("o")) {
                    double num1 = Double.parseDouble(stack.pop());
                    if (num1 > 0) {
                        res = Math.log(num1) / Math.log(2);
                    } else {
                        editText.setText("must be greater than 0");
                        indexYN = 1;
                    }
                } else if (item.equals("s")) {
                    double num1 = Double.parseDouble(stack.pop());
                    res = Math.sin(num1);
                } else if (item.equals("c")) {
                    double num1 = Double.parseDouble(stack.pop());
                    res = Math.cos(num1);
                } else if (item.equals("t")) {
                    double num1 = Double.parseDouble(stack.pop());
                    if (Math.cos(num1) != 0) {
                        res = Math.tan(num1);
                    } else {
                        editText.setText("cannot be+- (π/2+k π)");
                        indexYN = 1;
                    }
                }
                stack.push(String.valueOf(res));
            }
        }
        if (indexYN == 0) {
            if (!stack.isEmpty()) {
                return Double.parseDouble(stack.pop());
            } else {
                return 0;
            }
        } else {
            return -999999;
        }
    }

    public void estimate() {
        text.setText("");
        int i = 0;
        if (str.length() == 0) {
            text.setText("Input is empty！");
            indexYN = 1;
        }
        if (str.length() == 1) {
            if ("0123456789ep".indexOf(str.charAt(0)) == -1) {
                text.setText("1:error！");
                indexYN = 1;
            }
        }
        if (str.length() > 1) {
            for (i = 0; i < str.length() - 1; i++) {
                if ("losctg(0123456789ep".indexOf(str.charAt(0)) == -1) {
                    text.setText("2:error！");
                    indexYN = 1;
                }
                if ("+-*/".indexOf(str.charAt(i)) >= 0 && "0123456789losctg(ep".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("3:error！");
                    indexYN = 1;
                }
                if (str.charAt(i) == '.' && "0123456789".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("4:error！");
                    indexYN = 1;
                }
                if (str.charAt(i) == '!' && "+-*/^)".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("5:error！");
                    indexYN = 1;
                }
                if ("losctg".indexOf(str.charAt(i)) >= 0 && "0123456789(ep".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("6:error！");
                    indexYN = 1;
                }
                if (str.charAt(0) == '0' && str.charAt(1) == '0') {
                    text.setText("7:error！");
                    indexYN = 1;
                }
                if (i >= 1 && str.charAt(i) == '0') {
                    int m = i;
                    int n = i;
                    int is = 0;
                    if ("0123456789.".indexOf(str.charAt(m - 1)) == -1 && "+-*/.!^)".indexOf(str.charAt(i + 1)) == -1) {
                        text.setText("8:error！");
                        indexYN = 1;
                    }
                    if (str.charAt(m - 1) == '.' && "0123456789+-*/.^)".indexOf(str.charAt(i + 1)) == -1) {
                        text.setText("9:error！");
                        indexYN = 1;
                    }
                    n -= 1;
                    while (n > 0) {
                        if ("(+-*/^glosct".indexOf(str.charAt(n)) >= 0) {
                            break;
                        }
                        if (str.charAt(n) == '.') {
                            is++;
                        }
                        n--;
                    }
                    if ((is == 0 && str.charAt(n) == '0') || "0123456789+-*/.!^)".indexOf(str.charAt(i + 1)) == -1) {
                        text.setText("a:error！");
                        indexYN = 1;
                    }
                    if (is == 1 && "0123456789+-*/.^)".indexOf(str.charAt(i + 1)) == -1) {
                        text.setText("b:error！");
                        indexYN = 1;
                    }
                    if (is > 1) {
                        text.setText("c:error！");
                        indexYN = 1;
                    }
                }
                if ("123456789".indexOf(str.charAt(i)) >= 0 && "0123456789+-*/.!^)".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("d:error！");
                    indexYN = 1;
                }
                if (str.charAt(i) == '(' && "0123456789locstg()ep".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("e:error！");
                    indexYN = 1;
                }
                if (str.charAt(i) == ')' && "+-*/!^)".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("f:error！");
                    indexYN = 1;
                }
                if ("0123456789!)ep".indexOf(str.charAt(str.length() - 1)) == -1) {
                    text.setText("g:error！");
                    indexYN = 1;
                }
                if (i > 2 && str.charAt(i) == '.') {
                    int n = i - 1;
                    int is = 0;
                    while (n > 0) {
                        if ("(+-*/^glosct".indexOf(str.charAt(n)) >= 0) {
                            break;
                        }
                        if (str.charAt(n) == '.') {
                            is++;
                        }
                        n--;
                    }
                    if (is > 0) {
                        text.setText("h:error！");
                        indexYN = 1;
                    }
                }
                if ("ep".indexOf(str.charAt(i)) >= 0 && "+-*/^)".indexOf(str.charAt(i + 1)) == -1) {
                    text.setText("i:error！");
                    indexYN = 1;
                }
            }
        }
    }
}

