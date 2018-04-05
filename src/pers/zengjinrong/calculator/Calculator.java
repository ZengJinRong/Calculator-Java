package pers.zengjinrong.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private String number = "0";
    private boolean willTextInit = true;
    private String operator = "+";
    private final JTextField textField = new JTextField();

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }

    private Calculator() {
        super();
        init();
    }

    private void init() {
        setTitle("计算器");
        setResizable(false);
        setBounds(100, 100, 240, 240);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final JPanel textPanel = new JPanel();
        textField.setText("0");
        textField.setColumns(18);
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textPanel.add(textField);
        getContentPane().add(textPanel, BorderLayout.NORTH);

        final JPanel inputButtonPanel = new JPanel();
        final GridLayout buttonLayout = new GridLayout(0, 4);
        buttonLayout.setVgap(10);
        buttonLayout.setHgap(10);
        inputButtonPanel.setLayout(buttonLayout);
        getContentPane().add(inputButtonPanel, BorderLayout.SOUTH);
        JButton[][] inputButton = {
                {new BackspaceButton(), new ClearButton(), new ClearAllButton(), new OperatorButton("√")},
                {new NumberButton(1), new NumberButton(2), new NumberButton(3), new OperatorButton("+")},
                {new NumberButton(4), new NumberButton(5), new NumberButton(6), new OperatorButton("-")},
                {new NumberButton(7), new NumberButton(8), new NumberButton(9), new OperatorButton("*")},
                {new PointButton(), new NumberButton(0), new OperatorButton("="), new OperatorButton("/")},
        };
        for (int row = 0; row < inputButton.length; row++) {
            for (int col = 0; col < inputButton[0].length; col++) {
                final JButton button = inputButton[row][col];
                inputButtonPanel.add(button);
            }
        }
    }


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

        abstract void action();
    }


    class OperatorButton extends CalculatorButton {

        OperatorButton(String name) {
            super(name.substring(0, 1));
        }

        @Override
        void action() {
            double num = Double.valueOf(number);
            double textNum = Double.valueOf(textField.getText());
            if (num == 0) {
                number = textNum + "";
            } else {
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

                operator = this.getText();
                switch (operator.charAt(0)) {
                    case '√':
                        num = Math.sqrt(num);
                        break;
                }
                number = num + "";
            }
            textField.setText(number);
            willTextInit = true;

        }
    }


    class NumberButton extends CalculatorButton {
        NumberButton(Integer num) {
            super(num.toString().substring(0, 1));
        }

        @Override
        void action() {
            String text = textField.getText();
            if (willTextInit) {
                text = this.getText();
                willTextInit = false;
            } else {
                text = text + this.getText();
            }
            textField.setText(text);
        }
    }

    class PointButton extends CalculatorButton {
        PointButton() {
            super(".");
        }

        @Override
        void action() {
            String text = textField.getText();
            if (!text.contains(".")) {
                text = text + ".";
                textField.setText(text);
            }
        }
    }

    class BackspaceButton extends CalculatorButton {
        BackspaceButton() {
            super("←");
        }

        @Override
        void action() {
            String text = textField.getText();
            int length = text.length();
            if (length == 1) {
                textField.setText("0");
                willTextInit = true;
            } else {
                text = text.substring(0, length - 1);
                textField.setText(text);
            }
        }
    }

    class ClearButton extends CalculatorButton {
        ClearButton() {
            super("CE");
        }

        @Override
        void action() {
            textField.setText("0");
            willTextInit = true;
        }
    }

    class ClearAllButton extends CalculatorButton {
        ClearAllButton() {
            super("C");
        }

        @Override
        void action() {
            textField.setText("0");
            number = "0";
            willTextInit = true;
        }
    }

}

