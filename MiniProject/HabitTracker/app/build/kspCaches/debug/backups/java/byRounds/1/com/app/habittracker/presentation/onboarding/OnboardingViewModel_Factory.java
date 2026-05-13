package com.app.habittracker.presentation.onboarding;

import com.app.habittracker.data.local.PreferencesManager;
import com.app.habittracker.domain.repository.UserRepository;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  public OnboardingViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(userRepositoryProvider.get(), preferencesManagerProvider.get());
  }

  public static OnboardingViewModel_Factory create(Provider<UserRepository> userRepositoryProvider,
      Provider<PreferencesManager> preferencesManagerProvider) {
    return new OnboardingViewModel_Factory(userRepositoryProvider, preferencesManagerProvider);
  }

  public static OnboardingViewModel newInstance(UserRepository userRepository,
      PreferencesManager preferencesManager) {
    return new OnboardingViewModel(userRepository, preferencesManager);
  }
}
