package jPlus.generic;

/**
 * This is just a nice helper to make
 * putting object refs into a JFX combo box easier
 * and other nicities.
 * See, tossing an enum in there is easy as it's
 * toString method is used to determine the label
 * in the combobox. Then you can store or retrieve
 * any data in that enum.
 * <p>
 * But for something like a generic list of any
 * color, or unknown colors, named item helps a lot.
 * Then a combobox has a name for it's item's toString
 * and this grants you a container on your object.
 * <p>
 * So use:
 * <p>
 * ComboBox<NamedItem<MyObject>> combobox = new Combobox<>();
 * OR in fxml of course.
 * <p>
 * Then combobox.getItems().setAll(collectionOfNamedItems)
 * then combobox.valueProperty().addListener(this::handleComboChange)
 * create method, and change the annoying invalid listener to change listener.
 * Or any number of things. The resultant valueprop is your named item wrapper
 * with each of your items, so just do combo.getValue().item;
 *
 * @param <T>
 */
public class NamedItem<T> implements Named1<T> {
    public final String name;
    public final T item;

    public NamedItem(String name, T item) {
        this.name = name;
        this.item = item;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T retrieve() {
        return item;
    }

    @Override
    public String toString() {
        return name;
    }

    //***************************************************************//

    public static <E> NamedItem<E> blank() {
        return new NamedItem<>("null", null);
    }
}
