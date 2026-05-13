package com.app.habittracker.workers;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = HabitStatusWorker.class
)
public interface HabitStatusWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.app.habittracker.workers.HabitStatusWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(HabitStatusWorker_AssistedFactory factory);
}
