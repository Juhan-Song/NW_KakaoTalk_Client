import API.Weather;
import ImageResizer.ImageResizer;
import Object.ServerInfo;
import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainFrame extends JFrame {
    public static int totalUserNumber;
    private String user;

    private JPanel mainFrame;
    public JPanel mainPanel;
    private JPanel APIPanel;

    private JButton btnFriend;
    private JButton btnSearch;
    private JLabel lblAPI;
    private JButton btnChat;
    private JButton btnMore;
    private JPanel leftFrame;
    private JPanel fieldPanel;
    private JLabel lblField;
    private JButton btnAdd;
    private JButton btnLogout;


    private static JPanel target;
    public static JPanel getTarget() {
        return target;
    }
    public void setTarget(JPanel target) {
        this.target = target;
    }

    private ServerInfo info;
    private static Socket socket;
    public static Socket getSocket() {
        return socket;
    }
    public static void setSocket(Socket sck) {
        socket = sck;
    }
    private Scanner clientInput;
    private PrintWriter clientOutput;

    private static boolean isChange = false;
    public static boolean getIsChange() {
        return isChange;
    }
    public static void setIsChange(boolean isChange) {
        MainFrame.isChange = isChange;
    }
    private static boolean isLogout = false;
    public boolean isLogout() {
        return isLogout;
    }
    public static void setLogout(boolean logout) {
        isLogout = logout;
    }

    MainFrame(ServerInfo serverInfo, String nickName) {
        this.info = serverInfo;
        this.user = new String(nickName);

        try {
            socket = new Socket(info.serverIP, info.serverPort);
            System.out.println("[Socket]: " + socket);
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isLogout == true) {
                    isLogout = false;
                    dispose();
                } // ????????????
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isLogout == true) {
                    isLogout = false;
                    dispose();
                } // ????????????
            }
        }); // ????????? ?????????

        btnFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblField.setText("??????");

                mainPanel.removeAll();
                mainPanel.add(new FriendBoard(user, socket).getMainPanel());
                // ?????? ?????? ?????????

                mainPanel.revalidate();
                mainPanel.repaint();
                target = mainPanel;
            }
        }); // ?????? ?????? ????????????

        btnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }); // ?????? ?????? ????????????

        btnMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }); // ????????? ??????

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    clientInput = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    clientOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    // ?????? ????????? ??????

                    String logoutJSON = Util.createSingleJSON(3003, "logout", user);
                    clientOutput.println(logoutJSON);

                    if (clientInput.hasNextLine()) {
                        String serverOutput = new String();

                        try {
                            serverOutput = clientInput.nextLine();
                            if (serverOutput.isEmpty()) serverOutput = clientInput.nextLine();
                            // ??????????????? ??????

                            JSONParser parser = new JSONParser();
                            JSONObject object = (JSONObject) parser.parse(serverOutput);
                            // JSON ??????

                            int response = Integer.parseInt(String.valueOf(object.get("code")));
                            // ?????? ?????? ??????

                            if (response == 200) {
                                String logoutResult = String.valueOf(object.get("logout"));
                                System.out.println(logoutResult);
                                // ???????????? ?????? ??????

                                if (logoutResult.equals("true")) {
                                    JOptionPane.showMessageDialog(mainPanel, "Logout Success.", "Notice", JOptionPane.INFORMATION_MESSAGE);

                                    System.out.println("[Socket_Close]: " + socket);
                                    clientInput.close();
                                    clientOutput.close();
                                    socket.close();
                                    // ?????? ?????? ??????

                                    dispose();
                                } // ??????????????? ??? ??????
                                else {
                                    JOptionPane.showMessageDialog(mainPanel, "Logout Failed.", "Warning", JOptionPane.WARNING_MESSAGE);
                                } // ??????????????? ?????? ?????? ??????
                            } // ????????? ????????? ??????????????? ????????? ??????
                            else {
                                System.out.println("Logout Error");
                            } // ?????????????????? ????????? ??????
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }); // ????????????

        Weather weatherAPI = new Weather(); // API ??????
        String sky;
        if(weatherAPI.skyState==1){
            sky = "??????";
        }
        else if(weatherAPI.skyState==3){
            sky = "?????? ??????";
        }
        else{
            sky = "??????";
        } // API ?????? (??????)

        lblAPI.setText(
                "<html>" +
                        "<h1>" + "?????? ????????????" + "</h1>" +
                        "?????? ??????: " + sky + "<br>" +
                        "??????: " + weatherAPI.temperature + "<br>" +
                        "?????? ??????: " + weatherAPI.POP + "<br>" +
                        "</html>"
        ); // ?????? ?????? ?????? ???????????? ??????

        JButton[] btnGroup2 = new JButton[2];
        btnGroup2[0] = btnSearch;
        btnGroup2[1] = btnAdd;
        // ?????? ????????????
        ImageResizer.FriendBoardImage(btnGroup2);

        JButton[] btnGroup = new JButton[3];
        btnGroup[0] = btnFriend;
        btnGroup[1] = btnChat;
        btnGroup[2] = btnMore;
        // ?????? ?????? ??????
        ImageResizer.InterfaceImage(btnGroup);

        setContentPane(mainFrame);

        lblField.setText("??????");

        mainPanel.setLayout(new GridLayout(1, 1));
        mainPanel.add(new FriendBoard(user, socket).getMainPanel());
        target = mainPanel;
        // ?????? ?????? ????????????

        setSize(450, 600);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("KakaoTalk - " + user);
        setVisible(true);
    }
}
