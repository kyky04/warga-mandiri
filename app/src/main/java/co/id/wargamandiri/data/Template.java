package co.id.wargamandiri.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import co.id.wargamandiri.R;
import co.id.wargamandiri.template.InfoTemplate;


/**
 * @brief Enum for the templates that are included into toolkit application.
 * <p/>
 * Created by Abhishek on 23-05-2015.
 */
public enum Template {

    BASIC_M_LEARNING(R.string.basic_m_learning_title, R.string.basic_m_learning_description, R.drawable.info_template, R.string.info_template, InfoTemplate.class, R.string.info_assets_name);


    @StringRes
    final
    int type;
    private
    @DrawableRes
    final
    int image;
    private
    @StringRes
    final
    int title;
    private
    @StringRes
    final
    int description;
    private final Class<? extends TemplateInterface> templateClass;

    private
    @StringRes
    final
    int assetsName;

    Template(@StringRes int title, @StringRes int description, @DrawableRes int image, @StringRes int type, Class<? extends TemplateInterface> templateClass, @StringRes int assetsName) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.type = type;
        this.templateClass = templateClass;
        this.assetsName = assetsName;
    }

    public int getImage() {
        return image;
    }

    public int getTitle() {
        return title;
    }

    public int getDescription() {
        return description;
    }

    public Class<? extends TemplateInterface> getTemplateClass() {
        return templateClass;
    }

    public int getType() {
        return type;
    }

    public int getAssetsName() {
        return assetsName;
    }
}
