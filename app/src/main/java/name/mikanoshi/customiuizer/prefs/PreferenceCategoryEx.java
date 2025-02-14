package name.mikanoshi.customiuizer.prefs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import name.mikanoshi.customiuizer.R;

public class PreferenceCategoryEx extends PreferenceCategory {
	private final boolean dynamic;
	private final boolean empty;
	private boolean hidden;
	private boolean unsupported = false;
	private final Resources res = getContext().getResources();
	private final int childpadding = res.getDimensionPixelSize(R.dimen.preference_item_child_padding);

	public PreferenceCategoryEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		final TypedArray xmlAttrs = context.obtainStyledAttributes(attrs, R.styleable.PreferenceCategoryEx);
		dynamic = xmlAttrs.getBoolean(R.styleable.PreferenceCategoryEx_dynamic, false);
		empty = xmlAttrs.getBoolean(R.styleable.PreferenceCategoryEx_empty, false);
		hidden = xmlAttrs.getBoolean(R.styleable.PreferenceCategoryEx_hidden, false);
		xmlAttrs.recycle();
		setLayoutResource(R.layout.preference_category);
	}

	@Override
	public boolean onPrepareAddPreference(Preference preference) {
		preference.onParentChanged(this, shouldDisableDependents());
		return true;
	}

	@Override
	public void onBindViewHolder(PreferenceViewHolder view) {
		super.onBindViewHolder(view);
		TextView title = (TextView) view.findViewById(android.R.id.title);
		title.setText(getTitle() + (unsupported ? " ⨯" : (dynamic ? " ⟲" : "")));
		title.setVisibility(hidden || empty ? View.GONE : View.VISIBLE);
		View finalView = view.itemView;
		if (hidden) {
			finalView.setPadding(childpadding, 0, childpadding, 0);
		}
	}

	public void setUnsupported(boolean value) {
		unsupported = value;
		setEnabled(!value);
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void hide() {
		hidden = true;
		this.notifyChanged();
	}

	public void show() {
		hidden = false;
		this.notifyChanged();
	}

}
