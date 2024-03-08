#!/bin/bash

# copie des fichiers du dossier "dto" vers le dossier "dao"
cp ./dto/* ./dao
# Chemin vers le répertoire contenant les fichiers (modifier le chemin si nécessaire)
repertoire="./dao"
# se placer dans le dossier "dao"
cd "$repertoire"
# Afficher le répertoire de travail actuel (facultatif)
echo "Répertoire de travail actuel : $PWD"
# Renommage des fichiers qui finissent par "DTO" en "Repository"
for f in *.java; do 
    mv "$f" "${f/DTO/Repository}"; 
done

# Tableau pour stocker les noms des fichiers mis à jour
updated_files=()

for f in ./*.java; do 
    # Vérifier si le fichier est un fichier régulier lisible
    if [ -f "$f" ] && [ -r "$f" ]; then
        # Vérifier si le fichier contient la chaîne "record"
        if grep -q 'record' "$f"; then
            updated_files+=("$f")
            cp "$f" "${f}.bak"
            
            # Extraire le nom de l'interface à partir du nom de fichier
            interface_name=$(basename "$f" .java)
            
            # Extraire le nom de la classe DTO à partir du contenu du fichier
            dto_class=$(grep -oE 'public record \w+' "${f}.bak" | awk '{print $3}' | sed 's/DTO$//')
            
            # Extraire la première ligne avec le package
            package_line=$(grep -m 1 '^package' "${f}.bak")

             # Remplacer "dto" par "dao" dans la ligne package
            package_line_dao=$(echo "$package_line" | sed 's/dto/dao/')


            # Créer le contenu pour l'interface Repository
            repo_content="${package_line_dao}\n\nimport org.springframework.data.jpa.repository.JpaRepository;\n\npublic interface ${interface_name} extends JpaRepository<${dto_class}, Long> {}"
            
            # Ajouter le contenu de l'interface Repository au fichier
            echo -e "$repo_content" > "$f"
            
            rm "${f}.bak"

            echo "In vsCode : got to java project window on your left and click on the kebab icon then select clean workspace"
        fi
    else
        echo "Erreur: Impossible de lire le fichier $f"
    fi
done

# Afficher le contenu des fichiers mis à jour
for f in "${updated_files[@]}"; do
    echo "Nom du fichier $f mis à jour :"
    echo "$f"
done
