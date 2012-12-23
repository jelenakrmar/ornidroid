package fr.ornidroid.ui.multicriteriasearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import fr.ornidroid.R;

/**
 * The Class MultiCriteriaSelectField.
 * http://kevindion.com/2011/01/custom-xml-attributes-for-android-widgets/
 */
public class MultiCriteriaSelectField extends LinearLayout {

	/** The custom icon. */
	private boolean customIcon = false;

	/** The field type. */
	private MultiCriteriaSearchFieldType fieldType;
	/** The help icon. */
	private final ImageView helpIcon;

	/** The icon. */
	private final ImageView icon;
	/** The spinner. */
	private final Spinner spinner;

	/** The text view. */
	private final TextView textView;

	/**
	 * Instantiates a new multi criteria select field.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public MultiCriteriaSelectField(final Context context,
			final AttributeSet attrs) {
		super(context, attrs);

		this.textView = new TextView(context);
		this.icon = new ImageView(context);
		this.helpIcon = new ImageView(context);
		final LinearLayout layoutTextIcon = initTextIconLayout(context);

		this.spinner = new Spinner(context);
		this.spinner.setVisibility(View.GONE);
		final LayoutParams spinnerLayoutParams = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		spinnerLayoutParams.setMargins(30, 10, 30, 0);
		this.spinner.setLayoutParams(spinnerLayoutParams);
		this.spinner.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.custom_spinner));
		this.addView(layoutTextIcon);
		this.addView(this.spinner);

		// add click behaviour on the text
		this.textView.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				if (MultiCriteriaSelectField.this.spinner.getVisibility() == View.VISIBLE) {
					expand(false);
				} else {
					expand(true);
				}
			}
		});

		// add click behaviour on the help icon
		this.helpIcon.setOnClickListener(new OnClickListener() {
			public void onClick(final View v) {
				switch (MultiCriteriaSelectField.this.fieldType) {
				case CATEGORY:
					HelpDialog.getInstance(context).getDialogTitle()
							.setText(R.string.search_category);
					HelpDialog.getInstance(context).getDialogContent()
							.setText(R.string.search_category_help);
					break;
				default:
					HelpDialog.getInstance(context).getDialogTitle()
							.setText(R.string.search_no_help);
					HelpDialog.getInstance(context).getDialogContent()
							.setText("");
				}
				HelpDialog.getInstance(context).show();
			}
		});

		final TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MultiCriteriaSelectField);

		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			final int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.MultiCriteriaSelectField_text:
				final String myText = a.getString(attr);
				this.textView.setText(myText);
				break;
			case R.styleable.MultiCriteriaSelectField_spinnerPrompt:
				final String spinnerPrompt = a.getString(attr);
				this.spinner.setPrompt(spinnerPrompt);
				break;

			case R.styleable.MultiCriteriaSelectField_textBackground:
				final boolean textBackground = a.getBoolean(attr, false);
				if (textBackground) {
					this.textView
							.setBackgroundResource(R.color.mcs_text_background);
				}
				break;
			case R.styleable.MultiCriteriaSelectField_expand:
				final boolean expand = a.getBoolean(attr, false);
				if (expand) {
					this.spinner.setVisibility(View.VISIBLE);
				}
				break;

			}
		}
		a.recycle();

	}

	/**
	 * Expand or collapse the field.
	 * 
	 * @param expand
	 *            the expand
	 */
	public void expand(final boolean expand) {
		if (expand) {
			MultiCriteriaSelectField.this.spinner.setVisibility(View.VISIBLE);
			if (!this.customIcon) {
				MultiCriteriaSelectField.this.icon
						.setImageResource(R.drawable.ic_up);
			}
		} else {
			MultiCriteriaSelectField.this.spinner.setVisibility(View.GONE);
			if (!this.customIcon) {
				MultiCriteriaSelectField.this.icon
						.setImageResource(R.drawable.ic_down);
			}
		}
	}

	/**
	 * Gets the field type.
	 * 
	 * @return the field type
	 */
	public MultiCriteriaSearchFieldType getFieldType() {
		return this.fieldType;
	}

	/**
	 * Gets the spinner.
	 * 
	 * @return the spinner
	 */
	public Spinner getSpinner() {
		return this.spinner;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		this.spinner.setSelection(0);
	}

	/**
	 * Sets the field type.
	 * 
	 * @param fieldType
	 *            the new field type
	 */
	public void setFieldType(final MultiCriteriaSearchFieldType fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * Sets the icon resource.
	 * 
	 * @param resId
	 *            the new icon resource
	 */
	public void setIconResource(final int resId) {
		this.icon.setImageResource(resId);
		this.customIcon = true;
	}

	/**
	 * Inits the text icon layout.
	 * 
	 * @param context
	 *            the context
	 * @return the linear layout
	 */
	private LinearLayout initTextIconLayout(final Context context) {
		final LinearLayout layoutTextIcon = new LinearLayout(context);
		final LayoutParams paramsLayoutTextIcon = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layoutTextIcon.setLayoutParams(paramsLayoutTextIcon);

		// add the icon on the left side
		final LayoutParams paramsLayoutIcon = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		paramsLayoutIcon.height = 50;
		paramsLayoutIcon.width = 50;
		this.icon.setLayoutParams(paramsLayoutIcon);
		this.icon.setImageResource(R.drawable.ic_down);
		layoutTextIcon.addView(this.icon);

		// add a layout for the text
		final LinearLayout layoutText = new LinearLayout(context);

		final LayoutParams paramsLayoutText = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		paramsLayoutText.leftMargin = 20;
		paramsLayoutText.topMargin = 5;
		paramsLayoutText.gravity = Gravity.CENTER;
		layoutText.setLayoutParams(paramsLayoutText);
		layoutText.addView(this.textView);
		layoutTextIcon.addView(layoutText);

		// add the help icon
		this.helpIcon.setImageResource(R.drawable.ic_help);
		final LinearLayout layoutHelpIcon = new LinearLayout(context);
		final LayoutParams paramsLayoutHelpIcon = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		paramsLayoutHelpIcon.setMargins(0, 0, 20, 0);
		layoutHelpIcon.setHorizontalGravity(Gravity.RIGHT);

		layoutHelpIcon.setLayoutParams(paramsLayoutHelpIcon);
		layoutHelpIcon.addView(this.helpIcon);
		layoutTextIcon.addView(layoutHelpIcon);
		return layoutTextIcon;
	}
}
