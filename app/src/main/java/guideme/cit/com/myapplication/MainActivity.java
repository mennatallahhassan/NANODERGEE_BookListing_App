package guideme.cit.com.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String BOOK_LIST = "bookList";
     ListView listView;
     ArrayList<Book> bookList = new ArrayList<Book>();
     String word;
     EditText searchword;
     ImageButton searchBtn;
     GoogleBookTask googleBookTask;
     Adapter adapter;
     TextView again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        searchword = (EditText) findViewById(R.id.searchWord);

        searchBtn = (ImageButton) findViewById(R.id.searchBtn);

        if (isConnected == true) {


            listView = (ListView) findViewById(R.id.bookviewlist);
            again = (TextView) (findViewById(R.id.again));
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    word = searchword.getText().toString().replace(' ', '+');
                    again.setText("");

                    googleBookTask = new GoogleBookTask() {
                        @Override
                        protected void onPostExecute(String s) {
//                        TextView searchresult = new TextView(getApplicationContext());
//                        searchresult.setText("Search Result: ");
                            JsonParsing jsonParsing = new JsonParsing();
                            if (jsonParsing.parseJsonObject(s.toString()) != null) {
                                bookList = jsonParsing.parseJsonObject(s.toString());
                                adapter = new Adapter(getApplicationContext(), R.id.bookTitle, bookList);

                                listView.setAdapter(adapter);
                            } else {
                                adapter.clear();
                                again.setText("Search again with different words");
                            }

                        }
                    };
                    googleBookTask.execute("https://www.googleapis.com/books/v1/volumes?q=" + word);

                }
            });
        } else {
            searchword.setVisibility(View.INVISIBLE);
            searchBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
        }

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
