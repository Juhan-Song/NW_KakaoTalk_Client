import ImageResizer.ImageResizer;

import javax.swing.*;

public class UserInformation extends JFrame {
    private JPanel mainPanel;
    private JLabel lblNickname;
    private JLabel lblName;
    private JLabel lblMessage;
    private JLabel lblBirth;
    private JLabel lblPhone;
    private JLabel lblEmail;

    public UserInformation(String name, String nickname, String birth, String phone, String email, String message) {
        lblNickname.setText(nickname);
        lblMessage.setText(message);

        lblName.setText(name);
        lblBirth.setText(birth);
        lblPhone.setText(phone);
        lblEmail.setText(email);

        setContentPane(mainPanel);

        setSize(400, 300);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("유저 정보");
        setVisible(true);
    }
}
