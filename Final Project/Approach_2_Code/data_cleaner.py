import json
import os


if os.path.exists("conso_outfile_new.json"):
    print("Exists")
else:
    print("Does not Exist.")

folder_path = "chia_with_scope"

file_list = os.listdir(folder_path)

storage_dict = {}
i = 0
for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    if os.path.isfile(file_path):
        name_lst = file_name.split('.')

        file_id = name_lst[0]
        file_type = name_lst[1]

        if file_id not in storage_dict.keys():
            storage_dict[file_id] = {}
        
        if file_type == 'txt':
            with open(file_path, 'r') as file:
                contents = file.read()
            storage_dict[file_id]['Text'] = contents
        elif file_type == 'ann':
            with open(file_path, 'r') as file:
                contents = file.readlines()
            storage_dict[file_id]['Annotations'] = []
            for line in contents:
                if line[0] == 'T':
                    split_by_tab = line.split("\t")
                    if (';' not in split_by_tab[1]):
                        storage_dict[file_id]['Annotations'].append(split_by_tab[1])

json_obj = json.dumps(storage_dict, indent=4)

with open("conso_outfile_new.json", "w") as outfile:
    outfile.write(json_obj)

        


