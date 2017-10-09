package agency.tango.viking.mvp.core;

import java.io.Serializable;
import java.util.ArrayList;

public interface IDataWrapper {
    boolean getBooleanExtra(String name, boolean defaultValue);

    byte getByteExtra(String name, byte defaultValue);

    short getShortExtra(String name, short defaultValue);

    char getCharExtra(String name, char defaultValue);

    int getIntExtra(String name, int defaultValue);

    long getLongExtra(String name, long defaultValue);

    float getFloatExtra(String name, float defaultValue);

    double getDoubleExtra(String name, double defaultValue);

    String getStringExtra(String name);

    CharSequence getCharSequenceExtra(String name);

    Serializable getSerializableExtra(String name);

    ArrayList<Integer> getIntegerArrayListExtra(String name);

    ArrayList<String> getStringArrayListExtra(String name);

    ArrayList<CharSequence> getCharSequenceArrayListExtra(String name);

    boolean[] getBooleanArrayExtra(String name);

    byte[] getByteArrayExtra(String name);

    short[] getShortArrayExtra(String name);

    char[] getCharArrayExtra(String name);

    int[] getIntArrayExtra(String name);

    long[] getLongArrayExtra(String name);

    float[] getFloatArrayExtra(String name);

    double[] getDoubleArrayExtra(String name);

    String[] getStringArrayExtra(String name);

    CharSequence[] getCharSequenceArrayExtra(String name);

    boolean hasExtra(String paymentMethodSelected);

    void putExtra(String name, boolean value);

    void putExtra(String name, byte value);

    void putExtra(String name, short value);

    void putExtra(String name, char value);

    void putExtra(String name, int value);

    void putExtra(String name, long value);

    void putExtra(String name, float value);

    void putExtra(String name, double value);

    void putExtra(String name, String value);

    void putExtra(String name, CharSequence value);

    void putExtra(String name, Serializable value);

    void putExtra(String name, boolean[] value);

    void putExtra(String name, byte[] value);

    void putExtra(String name, short[] value);

    void putExtra(String name, char[] value);

    void putExtra(String name, int[] value);

    void putExtra(String name, long[] value);

    void putExtra(String name, float[] value);

    void putExtra(String name, double[] value);

    void putExtra(String name, String[] value);

    void putExtra(String name, CharSequence[] value);
}
