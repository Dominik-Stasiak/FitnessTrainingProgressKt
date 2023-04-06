package pl.edu.zut.trackfitnesstraining.daggerin

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepository
import pl.edu.zut.trackfitnesstraining.data.repository.FitnessRepositoryImpl
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepository
import pl.edu.zut.trackfitnesstraining.data.repository.UserRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFitnessRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): FitnessRepository {
        return FitnessRepositoryImpl(database, auth)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): UserRepository {
        return UserRepositoryImpl(database, auth)
    }
}