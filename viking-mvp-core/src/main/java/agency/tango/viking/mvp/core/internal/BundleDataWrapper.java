package agency.tango.viking.mvp.core.internal;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import agency.tango.viking.mvp.core.IDataWrapper;

public class BundleDataWrapper implements IDataWrapper {
  private final Bundle data;

  public BundleDataWrapper(@NonNull Bundle data) {
    this.data = data;
  }

  @Override
  public boolean getBooleanExtra(String name, boolean defaultValue) {
    return data.getBoolean(name, defaultValue);
  }

  @Override
  public byte getByteExtra(String name, byte defaultValue) {
    return data.getByte(name, defaultValue);
  }

  @Override
  public short getShortExtra(String name, short defaultValue) {
    return data.getShort(name, defaultValue);
  }

  @Override
  public char getCharExtra(String name, char defaultValue) {
    return data.getChar(name, defaultValue);
  }

  @Override
  public int getIntExtra(String name, int defaultValue) {
    return data.getInt(name, defaultValue);
  }

  @Override
  public long getLongExtra(String name, long defaultValue) {
    return data.getLong(name, defaultValue);
  }

  @Override
  public float getFloatExtra(String name, float defaultValue) {
    return data.getFloat(name, defaultValue);
  }

  @Override
  public double getDoubleExtra(String name, double defaultValue) {
    return data.getDouble(name, defaultValue);
  }

  @Override
  public String getStringExtra(String name) {
    return data.getString(name);
  }

  @Override
  public CharSequence getCharSequenceExtra(String name) {
    return data.getCharSequence(name);
  }

  public <T extends Parcelable> T getParcelableExtra(String name) {
    return data.getParcelable(name);
  }

  public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
    return data.getParcelableArrayList(name);
  }

  @Override
  public Serializable getSerializableExtra(String name) {
    return data.getSerializable(name);
  }

  @Override
  public ArrayList<Integer> getIntegerArrayListExtra(String name) {
    return data.getIntegerArrayList(name);
  }

  @Override
  public ArrayList<String> getStringArrayListExtra(String name) {
    return data.getStringArrayList(name);
  }

  @Override
  public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
    return data.getCharSequenceArrayList(name);
  }

  @Override
  public boolean[] getBooleanArrayExtra(String name) {
    return data.getBooleanArray(name);
  }

  @Override
  public byte[] getByteArrayExtra(String name) {
    return data.getByteArray(name);
  }

  @Override
  public short[] getShortArrayExtra(String name) {
    return data.getShortArray(name);
  }

  @Override
  public char[] getCharArrayExtra(String name) {
    return data.getCharArray(name);
  }

  @Override
  public int[] getIntArrayExtra(String name) {
    return data.getIntArray(name);
  }

  @Override
  public long[] getLongArrayExtra(String name) {
    return data.getLongArray(name);
  }

  @Override
  public float[] getFloatArrayExtra(String name) {
    return data.getFloatArray(name);
  }

  @Override
  public double[] getDoubleArrayExtra(String name) {
    return data.getDoubleArray(name);
  }

  @Override
  public String[] getStringArrayExtra(String name) {
    return data.getStringArray(name);
  }

  @Override
  public CharSequence[] getCharSequenceArrayExtra(String name) {
    return data.getCharSequenceArray(name);
  }

  @Override
  public boolean hasExtra(String name) {
    return data.containsKey(name);
  }

  @Override
  public void putExtra(String name, boolean value) {
    data.putBoolean(name, value);
  }

  @Override
  public void putExtra(String name, byte value) {
    data.putByte(name, value);
  }

  @Override
  public void putExtra(String name, short value) {
    data.putShort(name, value);
  }

  @Override
  public void putExtra(String name, char value) {
    data.putChar(name, value);
  }

  @Override
  public void putExtra(String name, int value) {
    data.putInt(name, value);
  }

  @Override
  public void putExtra(String name, long value) {
    data.putLong(name, value);
  }

  @Override
  public void putExtra(String name, float value) {
    data.putFloat(name, value);
  }

  @Override
  public void putExtra(String name, double value) {
    data.putDouble(name, value);
  }

  @Override
  public void putExtra(String name, String value) {
    data.putString(name, value);
  }

  @Override
  public void putExtra(String name, CharSequence value) {
    data.putCharSequence(name, value);
  }

  public void putExtra(String name, Parcelable value) {
    data.putParcelable(name, value);
  }

  public <T extends Parcelable> void putExtra(String name, ArrayList<T> value) {
    data.putParcelableArrayList(name, value);
  }

  @Override
  public void putExtra(String name, Serializable value) {
    data.putSerializable(name, value);
  }

  @Override
  public void putExtra(String name, boolean[] value) {
    data.putBooleanArray(name, value);
  }

  @Override
  public void putExtra(String name, byte[] value) {
    data.putByteArray(name, value);
  }

  @Override
  public void putExtra(String name, short[] value) {
    data.putShortArray(name, value);
  }

  @Override
  public void putExtra(String name, char[] value) {
    data.putCharArray(name, value);
  }

  @Override
  public void putExtra(String name, int[] value) {
    data.putIntArray(name, value);
  }

  @Override
  public void putExtra(String name, long[] value) {
    data.putLongArray(name, value);
  }

  @Override
  public void putExtra(String name, float[] value) {
    data.putFloatArray(name, value);
  }

  @Override
  public void putExtra(String name, double[] value) {
    data.putDoubleArray(name, value);
  }

  @Override
  public void putExtra(String name, String[] value) {
    data.putStringArray(name, value);
  }

  @Override
  public void putExtra(String name, CharSequence[] value) {
    data.putCharSequenceArray(name, value);
  }
}
