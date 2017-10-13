package teratroopers.teachercompanion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.codecrafters.tableview.TableView;
import android.graphics.Color;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import android.widget.Toast;

public class RegisterForm extends AppCompatActivity {

    static String[][] spaceProbes={
            {"1","Pioneer","Chemical","Jupiter"},
            {"2","Voyager","Plasma","Andromeda"},
            {"3","Casini","Solar","Saturn"},
            {"4","Spitzer","Anti-Matter","Andromeda"},
            {"5","Apollo","Chemical","Moon"},
            {"6","Curiosity","Solar","Mars"},
    };
    static String[] spaceProbeHeaders={"No","Name","Propellant","Destination"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        final TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        //SET PROP
        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,spaceProbeHeaders));
        tableView.setColumnCount(4);

        tableView.setDataAdapter(new SimpleTableDataAdapter(this, spaceProbes));

        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {
                Toast.makeText(getApplicationContext(), ((String[])clickedData)[1], Toast.LENGTH_SHORT).show();
            }
        });
    }
}

