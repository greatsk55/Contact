package com.example.user.contacts;

import android.app.ActionBar;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 16. 2. 13.
 */
public class PhonebookActivity extends ListActivity {
    public final int MAXIMUM_CHECK=5;
    String tag = "cap";
    String [] name, new_name;
    int count = 0;
    ListView listView;
    private ArrayAdapter<String>    m_Adapter;

    TextView text;
    Button button;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_item, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        text = (TextView) mCustomView.findViewById(R.id.invite);
        button = (Button) mCustomView.findViewById(R.id.button);

        Cursor cursor = getURI();  // 전화번호부 가져오기
        int end = cursor.getCount(); // 전화번호부의 갯수 세기
        name = new String[end];   // 전화번호부의 이름을 저장할 배열 선언

        if(cursor.moveToFirst()) {    //항상 처음에서 시작
            do {
                if(!cursor.getString(2).startsWith("01")) // 01로 시작하는 핸펀만
                    continue;         // 이멜주소만 있는것은 제외
                // 요소값 얻기
                name[count] = cursor.getString(1);  //이름
                name[count] += "\n";
                name[count] += cursor.getString(2);  //전번
                count++;
            } while(cursor.moveToNext());

            new_name = new String[count];    //이멜주소 제외한 01번호 가져오기
            for(int i=0; i<count; i++) new_name[i] = name[i];  //복사
        }
        cursor.close(); // 반드시 커서 닫고

        m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, new_name);
        listView = (ListView) findViewById(android.R.id.list);
        button = (Button) findViewById(R.id.button);

        listView.setAdapter(m_Adapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기버튼

        /*setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, // 멀티 쵸이스
                new_name));*/
        //listView = getListView();         // 리스트뷰

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  // 반드시 설정해줘야 멀티초이스

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mes = "";
                long num[] = listView.getCheckItemIds();  // 현재 체크된 id들의 배열 리턴
                if (num.length > MAXIMUM_CHECK) {        // 최대 선택가능 갯수 제한
                    listView.setItemChecked(position, false); // 강제로 언첵
                    Toast.makeText(PhonebookActivity.this,
                            MAXIMUM_CHECK + "개까지만 선택이 가능합니다.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if( num.length != 0 ){
                    button.setClickable(true);
                    button.setTextColor(R.color.black);
                }
                else{
                    button.setClickable(false);
                    button.setTextColor(R.color.white);
                }

                text.setText(""+num.length);
            }
        });
    }
    private Cursor getURI() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] { // 세개만 프로젝션함
                ContactsContract.Contacts._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        // 정렬방식 설정
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        return managedQuery(uri, projection, null, null, sortOrder);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void send(View v){

    }
}