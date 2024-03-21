import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private boolean isPlayerX;
    private boolean isComputerTurn;

    public TicTacToe() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        showHomePage();
    }

    private void showHomePage() {
        JPanel homePanel = new JPanel(new GridLayout(2, 1));
        JButton selectXButton = new JButton("Select X");
        JButton selectOButton = new JButton("Select O");
        Font font = new Font("Arial", Font.BOLD, 20);
        selectXButton.setFont(font);
        selectOButton.setFont(font);
        selectXButton.addActionListener(e -> {
            isPlayerX = true;
            isComputerTurn = false;
            startGame();
        });
        selectOButton.addActionListener(e -> {
            isPlayerX = false;
            isComputerTurn = true;
            startGame();
        });
        homePanel.add(selectXButton);
        homePanel.add(selectOButton);
        add(homePanel);
        setVisible(true);
    }

    private void startGame() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
        setVisible(true);
        if (isComputerTurn) {
            computerMove();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        if (buttonClicked.getText().equals("") && !isComputerTurn) {
            if (isPlayerX) {
                buttonClicked.setText("X");
            } else {
                buttonClicked.setText("O");
            }
            if (checkWinner()) {
                showResult("Player");
            } else {
                isComputerTurn = true;
                computerMove();
                if (checkWinner()) {
                    showResult("Computer");
                }
            }
        }
    }

    private void computerMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));
        buttons[row][col].setText(isPlayerX ? "O" : "X");
        isComputerTurn = false;
    }

    private boolean checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[0][i].getText().equals("") &&
                    buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }

        // Check diagonals
        if (!buttons[0][0].getText().equals("") &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) {
            return true;
        }
        if (!buttons[0][2].getText().equals("") &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText())) {
            return true;
        }

        return false;
    }

    private void showResult(String winner) {
        JOptionPane.showMessageDialog(this, winner + " wins!");
        resetBoard();
    }

    private void resetBoard() {
        getContentPane().removeAll();
        showHomePage();
        isComputerTurn = false;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
