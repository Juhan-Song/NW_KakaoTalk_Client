import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PopUpMenu {
    private JPopupMenu menu;
    private JMenuItem userInfoItem;
    private JMenuItem friendRequestItem;
    private JPanel target;

    private Socket socket;
    private Scanner clientInput;
    private PrintWriter clientOutput;

    public PopUpMenu(Socket socket, JPanel con, String user) {
        target = con;

        try {
            this.socket = socket;
            clientInput = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            clientOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        menu = new JPopupMenu();
        userInfoItem = new JMenuItem("유저 정보");
        friendRequestItem = new JMenuItem("친구 요청하기");

        userInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInfoJSON = Util.createSingleJSON(3004, "user", user);
                clientOutput.println(userInfoJSON);

                if (clientInput.hasNextLine()) {
                    String serverOutput = new String();

                    try {
                        serverOutput = clientInput.nextLine();
                        if (serverOutput.isEmpty()) clientInput.nextLine();

                        JSONParser parser = new JSONParser();
                        JSONObject object = (JSONObject) parser.parse(serverOutput);

                        int response = Integer.parseInt(String.valueOf(object.get("code")));

                        if (response == 200) {
                            String userInfoResult = String.valueOf(object.get("userInfo"));
                            System.out.println(userInfoResult);

                            if (userInfoResult.equals("true")) {
                                String name = String.valueOf(object.get("name"));
                                String nickname = String.valueOf(object.get("nickname"));
                                String birth = String.valueOf(object.get("birth"));
                                String phone = String.valueOf(object.get("phone"));
                                String email = String.valueOf(object.get("email"));
                                String message = String.valueOf(object.get("message"));

                                UserInformation userInformation = new UserInformation(name, nickname, birth, phone, email, message);
                            }
                            else {
                                JOptionPane.showMessageDialog(target, "Search Failed.", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        else {
                            System.out.println("Message Error");
                        }
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        friendRequestItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        menu.add(userInfoItem);
        menu.addSeparator();
        menu.add(friendRequestItem);
    }

    public void ShowPopUpMenu(MouseEvent e) {
        menu.show(target, e.getX(), e.getY());
    }
}
