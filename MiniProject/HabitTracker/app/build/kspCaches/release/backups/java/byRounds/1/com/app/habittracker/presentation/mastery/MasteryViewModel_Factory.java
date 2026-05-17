package com.app.habittracker.presentation.mastery;

import com.app.habittracker.domain.repository.HabitRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class MasteryViewModel_Factory implements Factory<MasteryViewModel> {
  private final Provider<HabitRepository> habitRepositoryProvider;

  public MasteryViewModel_Factory(Provider<HabitRepository> habitRepositoryProvider) {
    this.habitRepositoryProvider = habitRepositoryProvider;
  }

  @Override
  public MasteryViewModel get() {
    return newInstance(habitRepositoryProvider.get());
  }

  public static MasteryViewModel_Factory create(Provider<HabitRepository> habitRepositoryProvider) {
    return new MasteryViewModel_Factory(habitRepositoryProvider);
  }

  public static MasteryViewModel newInstance(HabitRepository habitRepository) {
    return new MasteryViewModel(habitRepository);
  }
}
