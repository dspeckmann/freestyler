package me.speckmann.danny;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * Dieser ArrayAdapter bindet ein Array von Wortpaketen an die entsprechende ListView der Activity.
 */
public class WordPackageAdapter extends ArrayAdapter <WordPackage>{
        WordPackage[] modelItems = null;
        Context context;
        public WordPackageAdapter(Context context, WordPackage[] resource) {
                super(context,R.layout.row_wordpackage,resource);
                this.context = context;
                this.modelItems = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_wordpackage, parent, false);
                TextView name = (TextView) convertView.findViewById(R.id.textView1);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
                name.setText(modelItems[position].getName());

                cb.setChecked(modelItems[position].getIsActive());

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (modelItems[position].getIsActive()) {
                            boolean anySelected = false;
                            for (int i = 0; i < modelItems.length; i++) {
                                if (i != position && modelItems[i].getIsActive()) {
                                    anySelected = true;
                                }
                            }
                            if (!anySelected) {
                                CheckBox checkBox = (CheckBox) view;
                                checkBox.setChecked(modelItems[position].getIsActive());
                                Log.d("[FreeStyler]", "Wird nicht abgewählt, da letztes Paket.");
                                return;
                            }
                        }
                        WordPackageDataSource wds = new WordPackageDataSource(getContext());
                        wds.open();
                        wds.toggleWordPackageIsActive(modelItems[position]);
                        wds.close();
                        modelItems[position].setIsActive(!modelItems[position].getIsActive());
                        CheckBox checkBox = (CheckBox) view;
                        checkBox.setChecked(modelItems[position].getIsActive());
                    }
                });

                return convertView;
        }
}
