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
public final class HabitStatusWorker_AssistedFactory_Impl implements HabitStatusWorker_AssistedFactory {
  private final HabitStatusWorker_Factory delegateFactory;

  HabitStatusWorker_AssistedFactory_Impl(HabitStatusWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public HabitStatusWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<HabitStatusWorker_AssistedFactory> create(
      HabitStatusWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HabitStatusWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<HabitStatusWorker_AssistedFactory> createFactoryProvider(
      HabitStatusWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HabitStatusWorker_AssistedFactory_Impl(delegateFactory));
  }
}
