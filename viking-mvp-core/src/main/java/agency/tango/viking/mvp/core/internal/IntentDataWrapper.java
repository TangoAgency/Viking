package agency.tango.viking.mvp.core.internal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

import agency.tango.viking.mvp.core.IDataWrapper;

public class IntentDataWrapper implements IDataWrapper {
  private final BundleDataWrapper data;

  @NonNull
  private Bundle resolveBundleFromIntent(@Nullable Intent intent) {
    if (intent == null || intent.getExtras() == null) {
      return new Bundle();
    }

    return intent.getExtras();
  }

  public IntentDataWrapper(@Nullable Intent data) {
    this.data = new BundleDataWrapper(resolveBundleFromIntent(data));
  }

  @Override
  public boolean getBooleanExtra(String name, boolean defaultValue) {
    return data.getBooleanExtra(name, defaultValue);
  }

  @Override
  public byte getByteExtra(String name, byte defaultValue) {
    return data.getByteExtra(name, defaultValue);
  }

  @Override
  public short getShortExtra(String name, short defaultValue) {
    return data.getShortExtra(name, defaultValue);
  }

  @Override
  public char getCharExtra(String name, char defaultValue) {
    return data.getCharExtra(name, defaultValue);
  }

  @Override
  public int getIntExtra(String name, int defaultValue) {
    return data.getIntExtra(name, defaultValue);
  }

  @Override
  public long getLongExtra(String name, long defaultValue) {
    return data.getLongExtra(name, defaultValue);
  }

  @Override
  public float getFloatExtra(String name, float defaultValue) {
    return data.getFloatExtra(name, defaultValue);
  }

  @Override
  public double getDoubleExtra(String name, double defaultValue) {
    return data.getDoubleExtra(name, defaultValue);
  }

  @Override
  public String getStringExtra(String name) {
    return data.getStringExtra(name);
  }

  @Override
  public CharSequence getCharSequenceExtra(String name) {
    return data.getCharSequenceExtra(name);
  }

  public <T extends Parcelable> T getParcelableExtra(String name) {
    return data.getParcelableExtra(name);
  }

  public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
    return data.getParcelableArrayListExtra(name);
  }

  @Override
  public Serializable getSerializableExtra(String name) {
    return data.getSerializableExtra(name);
  }

  @Override
  public ArrayList<Integer> getIntegerArrayListExtra(String name) {
    return data.getIntegerArrayListExtra(name);
  }

  @Override
  public ArrayList<String> getStringArrayListExtra(String name) {
    return data.getStringArrayListExtra(name);
  }

  @Override
  public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
    return data.getCharSequenceArrayListExtra(name);
  }

  @Override
  public boolean[] getBooleanArrayExtra(String name) {
    return data.getBooleanArrayExtra(name);
  }

  @Override
  public byte[] getByteArrayExtra(String name) {
    return data.getByteArrayExtra(name);
  }

  @Override
  public short[] getShortArrayExtra(String name) {
    return data.getShortArrayExtra(name);
  }

  @Override
  public char[] getCharArrayExtra(String name) {
    return data.getCharArrayExtra(name);
  }

  @Override
  public int[] getIntArrayExtra(String name) {
    return data.getIntArrayExtra(name);
  }

  @Override
  public long[] getLongArrayExtra(String name) {
    return data.getLongArrayExtra(name);
  }

  @Override
  public float[] getFloatArrayExtra(String name) {
    return data.getFloatArrayExtra(name);
  }

  @Override
  public double[] getDoubleArrayExtra(String name) {
    return data.getDoubleArrayExtra(name);
  }

  @Override
  public String[] getStringArrayExtra(String name) {
    return data.getStringArrayExtra(name);
  }

  @Override
  public CharSequence[] getCharSequenceArrayExtra(String name) {
    return data.getCharSequenceArrayExtra(name);
  }

  @Override
  public boolean hasExtra(String name) {
    return data.hasExtra(name);
  }

  @Override
  public void putExtra(String name, boolean value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, byte value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, short value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, char value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, int value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, long value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, float value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, double value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, String value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, CharSequence value) {
    data.putExtra(name, value);
  }

  public void putExtra(String name, Parcelable value) {
    data.putExtra(name, value);
  }

  public <T extends Parcelable> void putExtra(String name, ArrayList<T> value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, Serializable value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, boolean[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, byte[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, short[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, char[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, int[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, long[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, float[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, double[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, String[] value) {
    data.putExtra(name, value);
  }

  @Override
  public void putExtra(String name, CharSequence[] value) {
    data.putExtra(name, value);
  }
}
