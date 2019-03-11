package com.eg.easycontacts.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.eg.easycontacts.R;
import com.eg.easycontacts.bean.LocalContact;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    private ListView lv_contactList;

    private List<LocalContact> contactList;
    private String[] contactlistViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        lv_contactList = findViewById(R.id.lv_contactList);

        setTitle("ContactList");

        loadLocalContactList();
        addlisteners();
    }

    /**
     * 获取联系人
     */
    private List<LocalContact> getLocalContactList() {
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
                null, null, null);
        List<LocalContact> localContactList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String displayName = cursor.getString(1);
                String number = cursor.getString(2);
                LocalContact localContact = new LocalContact();
                localContact.setId(id);
                localContact.setDisplayName(displayName);
                localContact.setNumber(number);
                localContactList.add(localContact);
            }
            cursor.close();
        }
        return localContactList;
    }

    /**
     * 加载本地联系人列表
     */
    private void loadLocalContactList() {
        contactList = getLocalContactList();
        contactlistViewData = new String[contactList.size()];
        for (int i = 0; i < contactList.size(); i++) {
            LocalContact contact = contactList.get(i);
            contactlistViewData[i] = contact.getId() + " / " + contact.getDisplayName() + " / " + contact.getNumber();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ContactListActivity.this,
                R.layout.item_array_adapter, contactlistViewData);
        lv_contactList.setAdapter(arrayAdapter);
    }


    /**
     * 添加监听
     */
    private void addlisteners() {
        lv_contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ContactListActivity.this, contactList.get(position).getDisplayName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
