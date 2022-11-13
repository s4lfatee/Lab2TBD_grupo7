<template>
    <v-card class="mx-auto" elevation="2" outlined>
        <v-card-title class="text-h5">
            Iniciar sesión
        </v-card-title>
        <v-card-subtitle>
            Ingresar nombre de usuario
        </v-card-subtitle>
        <v-card-text>
            <v-text-field v-model="form.name" outlined :counter="25" />
        </v-card-text>
        <v-card-actions>
            <v-btn color="primary" depressed v-on:click='saveName()'>
                Iniciar sesión
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import axios from "axios";
export default {
    data() {
        return {
            form: {
                name: ''
            }
        }
    },
    methods: {
        saveName() {
            axios.get("http://localhost:8081/voluntario/name/" + localStorage.getItem('name'))
                .then(response => {
                    localStorage.setItem('id',response.data.id);
                    window.location.href = "http://localhost:3000/get_tasks"
                })  
            localStorage.setItem('name', this.form.name)
        }
    }
}

</script>
