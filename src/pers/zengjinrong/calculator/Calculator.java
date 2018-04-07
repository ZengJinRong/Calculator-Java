package pers.zengjinrong.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 基于Java的简易计算器实现
 *
 * @author ZengJInRong
 * @version 1.4.1
 */
public class Calculator extends JFrame {
    private final String TITLE ="计算器";

    private String number = "0";                                //记录被运算数，二元运算中的第一个数
    private boolean textInitFlag = true;                        //文本框显示初始化标记，为true时可重新输入数字
    private String operator = "+";                              //记录最后一次输入的运算符号
    private final JTextField textField = new JTextField();      //文本框，显示运算结果或当前输入数字

    /**
     * 主函数入口
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }

    /**
     * 计算器类构造函数
     */
    private Calculator() {
        super();
        WindowInit();
        textPanelInit();
        buttonsInit();
    }

    /**
     * 初始化窗口界面
     */
    private void WindowInit() {
        setTitle(TITLE);
        setResizable(false);
        setBounds(100, 100, 240, 240);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * 初始化文本框
     */
    private void textPanelInit() {
        final JPanel textPanel = new JPanel();
        textField.setText("0");
        textField.setColumns(18);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textPanel.add(textField);
        getContentPane().add(textPanel, BorderLayout.NORTH);
    }

    /**
     * 初始化按键
     */
    private void buttonsInit() {
        final JPanel inputButtonPanel = new JPanel();
        final GridLayout buttonLayout = new GridLayout(0, 4);
        buttonLayout.setVgap(10);
        buttonLayout.setHgap(10);
        inputButtonPanel.setLayout(buttonLayout);
        getContentPane().add(inputButtonPanel, BorderLayout.SOUTH);
        JButton[][] inputButtons = {
                {new BackspaceButton(), new ClearButton(), new ClearAllButton(), new OperatorButton("√")},
                {new NumberButton(1), new NumberButton(2), new NumberButton(3), new OperatorButton("+")},
                {new NumberButton(4), new NumberButton(5), new NumberButton(6), new OperatorButton("-")},
                {new NumberButton(7), new NumberButton(8), new NumberButton(9), new OperatorButton("*")},
                {new PointButton(), new NumberButton(0), new OperatorButton("="), new OperatorButton("/")},
        };

        //foreach循环遍历inputButtons
        for (JButton[] buttons : inputButtons) {
            for (final JButton button : buttons) {
                inputButtonPanel.add(button);
            }
        }
    }

    /**
     * 所有计算器按键的抽象父类
     */
    abstract class CalculatorButton extends JButton {
        CalculatorButton(String name) {
            this.setText(name);
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action();
                }
            });
        }

        //按键点击操作
        abstract void action();
    }

    /**
     * 运算符号键
     */
    class OperatorButton extends CalculatorButton {

        OperatorButton(String name) {
            super(name.substring(0, 1));
        }

        @Override
        void action() {
            double num = Double.valueOf(number);
            double textNum = Double.valueOf(textField.getText());
            if (num == 0) {
                //未记录有被运算数，则当前输入数字记录为被运算数
                num = textNum;
            } else {
                //已记录有被运算数，进行二元运算操作
                switch (operator.charAt(0)) {
                    case '+':
                        num = num + textNum;
                        break;
                    case '-':
                        num = num - textNum;
                        break;
                    case '*':
                        num = num * textNum;
                        break;
                    case '/':
                        num = num / textNum;
                        break;
                    default:
                        num = textNum;
                        break;
                }
            }
            operator = this.getText();

            //一元运算操作
            switch (operator.charAt(0)) {
                case '√':
                    num = Math.sqrt(num);
                    break;
            }
            number = num + "";

            textField.setText(number);
            textInitFlag = true;

        }
    }

    /**
     * 数字键
     */
    class NumberButton extends CalculatorButton {
        NumberButton(Integer num) {
            super(num.toString().substring(0, 1));
        }

        @Override
        void action() {
            String text = textField.getText();
            if (textInitFlag) {
                //当前输入覆盖原先显示的内容
                text = this.getText();
                textInitFlag = false;
            } else {
                //当前输入接在原先显示内容的末尾
                text = text + this.getText();
            }
            textField.setText(text);
        }
    }

    /**
     * 小数点键
     */
    class PointButton extends CalculatorButton {
        PointButton() {
            super(".");
        }

        @Override
        void action() {
            String text = textField.getText();
            //判断是否是第一个输入的小数点
            if (!text.contains(".")) {
                text = text + ".";
                textField.setText(text);
            }
        }
    }

    /**
     * 退格键
     */
    class BackspaceButton extends CalculatorButton {
        BackspaceButton() {
            super("←");
        }

        @Override
        void action() {
            String text = textField.getText();
            int length = text.length();
            if (length == 1) {
                //退格到底归零
                textField.setText("0");
                textInitFlag = true;
            } else {
                //退格操作
                text = text.substring(0, length - 1);
                textField.setText(text);
            }
        }
    }

    /**
     * 清除当前输入键
     */
    class ClearButton extends CalculatorButton {
        ClearButton() {
            super("CE");
        }

        @Override
        void action() {
            textField.setText("0");
            textInitFlag = true;
        }
    }

    /**
     * 清除所有记录键
     */
    class ClearAllButton extends CalculatorButton {
        ClearAllButton() {
            super("C");
        }

        @Override
        void action() {
            textField.setText("0");
            number = "0";
            textInitFlag = true;
        }
    }

}

