package guideme.cit.com.myapplication;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String BOOK_LIST = "bookList";
    ListView listView;
    ArrayList<Book> bookList = new ArrayList<>();
    String word;
    EditText searchword;
    ImageButton searchBtn;
    GoogleBookTask googleBookTask;
    Adapter adapter;
    TextView again;
    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchword = (EditText) findViewById(R.id.searchWord);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);


        listView = (ListView) findViewById(R.id.bookviewlist);
        again = (TextView) (findViewById(R.id.again));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cm = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                activeNetwork = cm.getActiveNetworkInfo();
                isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {

                    word = searchword.getText().toString().replace(' ', '+');
                    again.setText("");
                    if (word.length() != 0) {
                        googleBookTask = new GoogleBookTask() {
                            @Override
                            protected void onPostExecute(String s) {

                                JsonParsing jsonParsing = new JsonParsing();
                                if (jsonParsing.parseJsonObject(s) != null) {
                                    bookList = jsonParsing.parseJsonObject(s);
                                    adapter = new Adapter(getApplicationContext(), R.id.bookTitle, bookList);

                                    listView.setAdapter(adapter);
                                } else {
                                    adapter.clear();
                                    again.setText(getString(R.string.search_again));
                                }

                            }
                        };
                        googleBookTask.execute("https://www.googleapis.com/books/v1/volumes?q=" + word);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please type any word to search with", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }
            }

        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(BOOK_LIST, bookList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bookList = savedInstanceState.getParcelableArrayList(BOOK_LIST);
        adapter = new Adapter(getApplicationContext(), R.id.bookTitle, bookList);

        listView.setAdapter(adapter);

    }
}
