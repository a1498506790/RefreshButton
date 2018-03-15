package com.mir.refreshbutton;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018-03-15
 * @desc
 */

class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private String[] mContactNames; // 联系人名称字符串数组
    private LayoutInflater mInflater;
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> mResultList; // 最终结果（包含分组的字母）
    private List<String> mCharacterList; // 字母List

    public enum ITEM_TYPE{
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    public TestAdapter(Context context, String[] contactNames) {
        mContext = context;
        mContactNames = contactNames;
        mInflater = LayoutInflater.from(mContext);
        handlerContact();
    }

    private void handlerContact() {
        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < mContactNames.length; i++) {
            String pinyin = getPingYin(mContactNames[i]);
            map.put(pinyin, mContactNames[i]);
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        mResultList = new ArrayList<>();
        mCharacterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!mCharacterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    mCharacterList.add(character);
                    mResultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!mCharacterList.contains("#")) {
                        mCharacterList.add("#");
                        mResultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }
            mResultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            View view = mInflater.inflate(R.layout.item_character, parent, false);
            return new CharacterHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.item_contact, parent, false);
            return new ContactHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(mResultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).mTextView.setText(mResultList.get(position).getmName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mResultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return mResultList == null ? 0 : mResultList.size();
    }

    public String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = inputString.trim().toCharArray();
        String output = "";
        try {
            for (char curchar : input) {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                } else {
                    output += java.lang.Character.toString(curchar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public CharacterHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ContactHolder(View view) {
            super(view);

            mTextView = view.findViewById(R.id.contact_name);
        }
    }

    public int getScrollPosition(String character) {
        if (mCharacterList.contains(character)) {
            for (int i = 0; i < mResultList.size(); i++) {
                if (mResultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }
        return -1; // -1不会滑动
    }

}
