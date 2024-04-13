import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CampaignDetails extends JFrame {
    private JTextField dateTextField;
    private JTextArea detailsTextArea;
    private JButton showDetailsButton;
    private Map<String, String> campaignDetails;
    private Connection connection;
    private PreparedStatement selectStatement;

    public CampaignDetails() {
        campaignDetails = new HashMap<>();
        campaignDetails.put("2023-08-05","On August 5th, 2023, the Bharat Jodo Yatra was inaugurated by Congress Leader Rahul Gandhi. This monumental event marked the beginning of a journey from Kanyakumari in the south to the picturesque landscapes of Kashmir in the north. The yatra aimed to bridge regional gaps, celebrate Indias diversity, and promote unity among its citizens. Thousands of enthusiastic participants from various walks of life joined the yatra, marching together for a stronger and more united nation.");

        setTitle("Campaign Details");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        initializeDatabase(); 
        setVisible(true);
    }

    private void initComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dateTextField = new JTextField(10);
        showDetailsButton = new JButton("Show Details");
        inputPanel.add(new JLabel("Enter Date: "));
        inputPanel.add(dateTextField);
        inputPanel.add(showDetailsButton);
    
        detailsTextArea = new JTextArea(10, 30);
        detailsTextArea.setEditable(false);
    
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);
    
        JLabel titleLabel = new JLabel("Campaign Event");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Adjust font size and style as needed
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
    
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
    
        contentPane.add(titlePanel, BorderLayout.NORTH); // Add title panel
        contentPane.add(inputPanel, BorderLayout.CENTER);
        contentPane.add(outputPanel, BorderLayout.SOUTH); // Changed to BorderLayout.SOUTH
    
        showDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputDate = dateTextField.getText();
                if (campaignDetails.containsKey(inputDate)) {
                    String details = campaignDetails.get(inputDate);
                    detailsTextArea.setText(details);
                    showRouteOnMap(inputDate); // Show route details on map
                } else {
                    detailsTextArea.setText("No details found for this date.");
                }
            }
        });
    }
    

    private void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/Campaigning";
            String username = "root";
            String password = "Sanjay#0806";

            connection = DriverManager.getConnection(url, username, password);
            selectStatement = connection.prepareStatement("SELECT details FROM campaigns WHERE date = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param date
     */
    private void showRouteOnMap(String date) {
        // ... Show route details on map ...

        try {
            selectStatement.setString(1, date);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                String details = resultSet.getString("details");
                detailsTextArea.setText(details);
            } else {
                detailsTextArea.setText("No details found for this date.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CampaignDetails();
            }
        });
    }
}
