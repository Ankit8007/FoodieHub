package com.app.foodiehub.appServices.firebase

import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.presentation.model.UpdateUserDetailModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await


class DBServices {
    companion object {
        private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

        private var userCollection: DatabaseReference = database.child("users")

        private var productCollection: DatabaseReference = database.child("product")

        private var bannerCollection: DatabaseReference = database.child("banners")

        suspend fun createUser(uid: String, data: UserDetailDataModel): Boolean {
            try {
                userCollection.child(uid).setValue(data).await()
                return true
            } catch (e: DatabaseException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun getUser(id: String): UserDetailDataModel? {
            try {
                val snapshot = userCollection.child(id).get().await()
                val data = snapshot.getValue(UserDetailDataModel::class.java)
                return data
            } catch (e: DatabaseException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun updateUser(id: String, data: UpdateUserDetailModel): Boolean {
            try {
                userCollection.child(id).updateChildren(data.toMap()).await()
                return true
            } catch (e: DatabaseException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }


        suspend fun getBanners():List<BannerDataModel> {
            try {
                val snapshot = bannerCollection.get().await()

                val bannerList: MutableList<BannerDataModel> = mutableListOf()

                for (bannerSnapshot in snapshot.children) {
                    val banner: BannerDataModel? =
                        bannerSnapshot.getValue(BannerDataModel::class.java)
                    if (banner != null) {
                        bannerList.add(banner)
                    }
                }
                return bannerList
            } catch (e: DatabaseException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }


        suspend fun getProductList(): List<ProductDataModel> {
            try {
                val snapshot = productCollection.get().await()

                val productList: MutableList<ProductDataModel> = mutableListOf()

                for (productSnapshot in snapshot.children) {
                    val product: ProductDataModel? =
                        productSnapshot.getValue(ProductDataModel::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                return productList
            } catch (e: DatabaseException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }
}