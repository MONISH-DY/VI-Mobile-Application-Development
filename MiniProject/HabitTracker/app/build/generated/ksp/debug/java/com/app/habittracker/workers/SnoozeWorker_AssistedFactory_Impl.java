package com.app.habittracker.workers;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SnoozeWorker_AssistedFactory_Impl implements SnoozeWorker_AssistedFactory {
  private final SnoozeWorker_Factory delegateFactory;

  SnoozeWorker_AssistedFactory_Impl(SnoozeWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public SnoozeWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<SnoozeWorker_AssistedFactory> create(
      SnoozeWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SnoozeWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<SnoozeWorker_AssistedFactory> createFactoryProvider(
      SnoozeWorker_Factory delegateFactory) {
    return InstanceFactory.create(new SnoozeWorker_AssistedFactory_Impl(delegateFactory));
  }
}
