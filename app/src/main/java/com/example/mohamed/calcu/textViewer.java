package com.example.mohamed.calcu;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Zezo on 10/13/2017.
 */

public class textViewer {

    private String viewedText;
    private Activity activity;
    private TextView textView;
    private boolean writeOnce;
    private String lastChar;
    private byte braceOn ;


    public textViewer(Activity _activity) {
        this.activity = _activity;
        viewedText = "";
        lastChar = "";
        textView = ((TextView) this.activity.findViewById(R.id.textView));
        writeOnce = true;
        braceOn = 0;
    }

    private boolean isOp(String s){
        switch (s){
            case "×":
            case "÷":
            case "+":
            case "-":
                return true;
            default:
                return false;
        }
    }
    private boolean isDigit(String s) {
        switch (s) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "ᴨ":
            case "e":
                return true;
            default:
                return false;
        }
    }

    public void insert(int ID) {
        viewedText = ((Button) this.activity.findViewById(ID)).getText().toString();
        if (textView.getText().toString().length() != 0)
            lastChar = "" + textView.getText().toString().charAt(textView.getText().toString().length() - 1);
        else {lastChar="";}
        identify();
    }


    private void insertDigit() {
        switch (lastChar){
            case "%":
            case ")":
                textView.setText(textView.getText() + "×" + viewedText);
                break;
            default:
                textView.setText(textView.getText() + viewedText);
        }
        writeOnce = true;
    }

    private void insertOp() {
        if (writeOnce && !lastChar.equals(".") && !lastChar.equals("")) {
            switch (viewedText) {
                case "+":
                case "-":
                    textView.setText(textView.getText() + viewedText);
                    writeOnce = false;
                    break;
                case "×":
                case "÷":
                    if (textView.getText().toString().length() != 0) {
                        textView.setText(textView.getText() + viewedText);
                        writeOnce = false;
                    }
                    break;
            }
        }
    }

    private void insertDot() {
        if (isDigit(lastChar))
            textView.setText(textView.getText() + viewedText);
    }
    private void insertPercent(){
        if(isDigit(lastChar) || lastChar.equals(")")){
            textView.setText(textView.getText() + viewedText);
        }
    }

    private void insertPower(){
        if(!lastChar.equals(".") && !isOp(lastChar) && lastChar.length() != 0 && !lastChar.equals("^"))
            textView.setText(textView.getText() + viewedText);
        writeOnce = false;
    }
    private void insertDoubleZero(){
        if(lastChar.equals(")") || lastChar.equals("") || isOp(lastChar) || lastChar.equals("^") || lastChar.equals("%"))
            textView.setText(textView.getText() + "0");
        else
            textView.setText(textView.getText() + viewedText);
    }

    private void insertTri(){

        if(!lastChar.equals(".") && (isOp(lastChar) || lastChar.length() == 0 || lastChar.equals("^")))
            switch (viewedText) {
                case "|x|":
                    textView.setText(textView.getText() + "abs(");
                    break;
                default:
                    textView.setText(textView.getText() + viewedText);
                    break;
            }
        else if(!lastChar.equals(".") && (lastChar.equals(")") || lastChar.equals("%") || isDigit(lastChar)))
            switch (viewedText) {
                case "|x|":
                    textView.setText(textView.getText() + "×abs(");
                    break;
                default:
                    textView.setText(textView.getText() + "×" + viewedText);
                    break;
            }
    }

    private double calculate(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            boolean eat(int charToEat) {
                while (ch == ' ') {
                    nextChar();
                }
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;}
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                for (;;) {

                    if (eat('+')) {
                        x += parseTerm();
                    }
                    else if (eat('-')) {
                        x -= parseTerm();

                    }

                    else {
                        return x;
                    }
                }
            }
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('×')) {
                        x *= parseFactor();
                    }
                    else if (eat('/')) {
                        x /= parseFactor();
                    }
                    else if (eat('%')) {
                        x %= parseFactor();
                    }
                    else {
                        return x;
                    }
                }
            }
            double parseFactor() {
                if (eat('+')) {
                    return parseFactor();
                }
                if (eat('-')) {
                    return -parseFactor();
                }
                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                }
                else if (eat('e')) {
                    x = Math.E;
                }
                else if (eat('ᴨ')) {
                    x = Math.PI;
                }
                else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') {
                        nextChar();
                    }
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
                else if (ch >= 'a' && ch <= 'z' || ch == '√') {
                    while (ch >= 'a' && ch <= 'z' || ch == '√') {
                        nextChar();
                    }
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "√":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        case "log":
                            x = Math.log(Math.toRadians(x));
                            break;
                        case "e":
                            x = Math.exp(Math.toRadians(x));
                            break;
                        case "ran":
                            x = Math.rint(Math.toRadians(x));
                            break;
                        case "aSin":
                            x = Math.toDegrees(Math.asin(x));
                            break;
                        case "aTan":
                            x = Math.toDegrees(Math.atan(x));
                            break;
                        case "aCos":
                            x = Math.toDegrees(Math.acos(x));
                            break;
                        case "ln":
                            x = Math.log1p(Math.toRadians(x));
                            break;
                        case "abs":
                            x= Math.abs(x);
                            break;

                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                }
                else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                if (eat('^')) {
                    x = Math.pow(x, parseFactor());
                }
                return x;


            }
        }.parse();
    }

    private void del(){
        braceOn = 0;
        String temp = textView.getText().toString();
        if (temp.equals("Error") || temp.equals("cannot divide by 0")) {
            textView.setText("");
        }
        else if (!temp.isEmpty()) {
            temp = textView.getText().toString().substring(0, textView.getText().length() - 1);
            if (temp.length()>= 4) {
                switch (temp.substring(temp.length() - 4)) {
                    case "aCos":
                    case "aSin":
                    case "aTan":
                        temp = temp.substring(0, temp.length() - 4);
                        break;
                }
            }
            if (temp.length() >= 3) {
                switch (temp.substring(temp.length() - 3)) {
                    case "cos":
                    case "sin":
                    case "tan":
                    case "log":
                    case "abs":
                        temp = temp.substring(0, temp.length() - 3);
                        break;
                }  }
            else if (temp.length() >= 2){
                switch (temp.substring(temp.length() - 2)) {
                    case "ln":
                        temp = temp.substring(0, temp.length() - 2);
                        break;
                }}
            else if (temp.length()>= 4) {
                switch (temp.substring(temp.length() - 4)) {
                    case "aCos":
                    case "aSin":
                    case "aTan":
                        temp = temp.substring(0, temp.length() - 4);
                        break;
                }
            }


            textView.setText(temp);
        }
    }


    private void calc(){braceOn = 0; textView.setText(""+calculate(textView.getText().toString()));}


    private void  more () {
        ((Button)this.activity.findViewById(R.id.btn1)).setText("sin(");
        ((Button)this.activity.findViewById(R.id.btn2)).setText("cos(");
        ((Button)this.activity.findViewById(R.id.btn3)).setText("tan(");
        ((Button)this.activity.findViewById(R.id.btn4)).setText("ln(");
        ((Button)this.activity.findViewById(R.id.btn5)).setText("√");
        ((Button)this.activity.findViewById(R.id.btn6)).setText("|x|");
        ((Button)this.activity.findViewById(R.id.btn7)).setText("ᴨ");
        ((Button)this.activity.findViewById(R.id.btn8)).setText("e");
        ((Button)this.activity.findViewById(R.id.btn9)).setText("log(");
        ((Button)this.activity.findViewById(R.id.btn0)).setText("aSin(");
        ((Button)this.activity.findViewById(R.id.btn00)).setText("aCos(");
        ((Button)this.activity.findViewById(R.id.btndot)).setText("aTan(");
        ((Button)this.activity.findViewById(R.id.btnmore)).setText("Less");
    }
    private void less (){
        ((Button)this.activity.findViewById(R.id.btn1)).setText("1");
        ((Button)this.activity.findViewById(R.id.btn2)).setText("2");
        ((Button)this.activity.findViewById(R.id.btn3)).setText("3");
        ((Button)this.activity.findViewById(R.id.btn4)).setText("4");
        ((Button)this.activity.findViewById(R.id.btn5)).setText("5");
        ((Button)this.activity.findViewById(R.id.btn6)).setText("6");
        ((Button)this.activity.findViewById(R.id.btn7)).setText("7");
        ((Button)this.activity.findViewById(R.id.btn8)).setText("8");
        ((Button)this.activity.findViewById(R.id.btn9)).setText("9");
        ((Button)this.activity.findViewById(R.id.btn0)).setText("0");
        ((Button)this.activity.findViewById(R.id.btn00)).setText("00");
        ((Button)this.activity.findViewById(R.id.btndot)).setText(".");
        ((Button)this.activity.findViewById(R.id.btnmore)).setText("More");
    }

    private void identify() {
        if (isDigit(viewedText) || viewedText.equals("√"))
            insertDigit();
        else if(isOp(viewedText))
            insertOp();
        else {
            switch (viewedText) {
                case ".":
                    insertDot();
                    break;
                case "%":
                    insertPercent();
                    break;
                case "^":
                    insertPower();
                    break;
                case "00":
                    insertDoubleZero();
                    break;
                case "More":
                    more();
                    break;
                case "Less":
                    less();
                    break;
                case "sin(":
                case "cos(":
                case "tan(":
                case "aSin(":
                case "aCos(":
                case "aTan(":
                case "log(":
                case "(":
                case "ln(":
                case "|x|":
                    insertTri();
                    break;
                case "=":
                    try {
                        calc();
                    }catch (Exception e){textView.setText("Error");}

                    break;
                case "Del":
                    del();
                    break;
                case "C":
                    textView.setText("");
                    break;
                case ")":
                    textView.setText(textView.getText() + viewedText);
                    break;




            }
        }
    }
}



