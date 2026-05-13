package com.app.habittracker.presentation.landing;

import com.app.habittracker.domain.repository.AuthRepository;
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
public final class LandingViewModel_Factory implements Factory<LandingViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public LandingViewModel_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LandingViewModel get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static LandingViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider) {
    return new LandingViewModel_Factory(authRepositoryProvider);
  }

  public static LandingViewModel newInstance(AuthRepository authRepository) {
    return new LandingViewModel(authRepository);
  }
}
