package com.example.realm1.model;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        /************************************************
         // Version 2
         class Protucto                   // add a new model class
         String nombre;
         String precio;

         class Restaurante
         @PrimaryKey
         String id;
         String nombre;
         String descripcion;
         RealmList<Protucto> protuctos;    // add an array property
         ************************************************/
        if(oldVersion == 0) {
            Log.d("Migration", "actualitzant a la versió 1");
            // Create a new class
            RealmObjectSchema productSchema = schema.create("Product")
                    .addField("nombre", String.class)
                    .addField("precio", String.class);

            schema.get("Restaurante")
                    .addRealmListField("products", productSchema)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            if (obj.getString("nombre").equals("123")) {
                                DynamicRealmObject product = realm.createObject("Product");
                                product.setString("nombre", "1111111");
                                product.setString("precio", "12");
                                obj.getList("products").add(product);
                            }
                        }
                    });
            oldVersion++;
        }
    }
    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Migration);
    }
}
