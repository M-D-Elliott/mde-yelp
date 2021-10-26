package jPlus.generic;

import jPlus.util.FileUtils;
import jPlus.util.Resources;
import jPlus.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public abstract class NamedItemsList<T> extends TreeSet<NamedItem<T>> {

    public NamedItemsList() {
        super(Comparator.comparing(NamedItem::toString));
    }

    public NamedItemsList(Comparator<NamedItem<T>> comparator) {
        super(comparator);
    }

    public NamedItemsList<T> init(File file) {
        return init(FileUtils.read(file.getPath()));
    }

    public NamedItemsList<T> init(InputStream inputStream) {
        return init(Resources.read(inputStream));
    }

    public NamedItemsList<T> init(List<String> textLines) {
        clear();

        for (String textLine : textLines) {
            String[] items = textLine.split(",");

            final String prettyName = StringUtils.capitalizeAllWords(items[0]);
            final T newEntry = createItem(items);
            final NamedItem<T> namedItem = new NamedItem<>(prettyName, newEntry);

            add(namedItem);
        }

        return this;
    }

    //***************************************************************//

    protected abstract T createItem(String... items);

    public String[] getNames() {
        return stream().map(NamedItem::toString).toArray(String[]::new);
    }
}
