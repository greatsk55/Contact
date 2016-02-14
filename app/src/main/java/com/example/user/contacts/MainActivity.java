package com.example.user.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    public RecyclerView recyclerView;
    public ContactsRecyclerViewAdapter contactAdapter;

    public String name;
    public String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openContactsIntent();
    }

    public void openContactsIntent(){
        Intent intent = new Intent(this, PhonebookActivity.class);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void showContactsList(){
        /*
        recyclerView = (RecyclerView) findViewById(R.id.list);

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ContactsList contactsList = new ContactsList(this);
        java.util.ArrayList<ContactsList.Member> list = contactsList.getMemberList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).log("abd");
        }

        contactAdapter = new ContactsRecyclerViewAdapter(this,
                list, (LinearLayoutManager) recyclerView.getLayoutManager());
        recyclerView.setAdapter(contactAdapter);
        */
    }
}
