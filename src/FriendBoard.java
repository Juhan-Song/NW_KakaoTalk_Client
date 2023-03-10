import Object.ServerInfo;
import User.User;
import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FriendBoard extends JFrame {
    private String user;
    private ServerInfo info;

    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JScrollPane scrFriend;
    private JPanel container;
    private JTextField txtSearch;

    private Socket socket;
    public Socket getSocket() {
        return socket;
    }
    private Scanner clientInput;
    public Scanner getClientInput() {
        return clientInput;
    }
    public PrintWriter getClientOutput() {
        return clientOutput;
    }

    private PrintWriter clientOutput;

    private ArrayList<User> searched;


    FriendBoard(String user, Socket socket) {
        this.user = user;

        try{
            this.socket = socket;
            clientInput = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            clientOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch(Exception e) {
            e.printStackTrace();
        } // 소켓 연결

        searched = new ArrayList<User>();
        container.setLayout((new GridLayout(1000, 1)));
        scrFriend.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // 기본 설정

        String userJSON = Util.createSingleJSON(3001, "user", user);
        clientOutput.println(userJSON);
        // 서버에게 유저 정보 요청

        if (clientInput.hasNextLine()) {
            String serverOutput = new String();

            try {
                serverOutput = clientInput.nextLine();
                if (serverOutput.isEmpty()) clientInput.nextLine();
                // 서버로부터 결과 수신

                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(serverOutput);
                // JSON 파싱

                int response = Integer.parseInt(String.valueOf(object.get("code")));
                // 응답 코드 확인

                if (response == 200) {
                    String message = String.valueOf(object.get("msg"));

                    JPanel con = new Container(user, socket).getContainer();

                    container.add(con);
                    // 유저 정보 화면에 출력
                } // 서버가 요청을 정상적으로 처리한 경우
                else {
                    System.out.println("Message Error");
                } // 비정상적으로 처리된 경우
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        txtSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String searchJSON = Util.createSingleJSON(3002, "search", txtSearch.getText());
                clientOutput.println(searchJSON);
                container.removeAll();
                // 서버에게 검생 요청

                if (clientInput.hasNextLine()) {
                    String serverOutput = new String();

                    try {
                        serverOutput = clientInput.nextLine();
                        if (serverOutput.isEmpty()) serverOutput = clientInput.nextLine();
                        // 서버로부터 결과 수신

                        JSONParser parser = new JSONParser();
                        JSONObject object = (JSONObject) parser.parse(serverOutput);
                        // JSON 파싱

                        int response = Integer.parseInt(String.valueOf(object.get("code")));
                        // 응답 코드 확인

                        if (response == 200) {
                            String searchResult = String.valueOf(object.get("search"));
                            System.out.println(searchResult);
                            // 서버의 검색 결과 확인

                            if (searchResult.equals("true")) {
                                ArrayList<String> searchedUser = new ArrayList<String>(Arrays.asList(String.valueOf(object.get("searched")).split(",")));
                                ArrayList<String> userMessage = new ArrayList<String>(Arrays.asList(String.valueOf(object.get("message")).split(",")));
                                for (int i = 0; i < searchedUser.size(); i++) {
                                    JPanel con = new Container(searchedUser.get(i), socket).getContainer();
                                    container.add(con);

                                    System.out.println(searchedUser.get(i));
                                    // lbl 추가
                                }
                            } // 유저를 찾은 경우
                            else {
                                container.removeAll();
                            } // 유저를 찾지 못한 경우
                            container.revalidate();
                            container.repaint();

                            MainFrame.getTarget().removeAll();
                            MainFrame.getTarget().add(mainPanel);
                            txtSearch.requestFocus();
                            // 메인 프레임에 새로고칭
                        } else {
                            System.out.println("Search Error");
                        }
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } // 서버로부터 결과를 수신한 경우
            }
        }); // 검색 기능
    }
}
