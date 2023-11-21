import os
import json

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
            contents = contents.replace("\n", ". ")
            storage_dict[file_id]['Text'] = contents
        elif file_type == 'ann':
            with open(file_path, 'r') as file:
                contents = file.readlines()
            storage_dict[file_id]['Annotations'] = []
            for line in contents:
                if line[0] == 'T':
                    space_index = max(3, line.find(' '))
                    tab_index = max(3, line.find('\t'))
                    start_index = min(space_index, tab_index)
                    line = line[start_index:].strip()
                    line = line.replace("\t", "--*--")
                    storage_dict[file_id]['Annotations'].append(line.strip())

json_obj = json.dumps(storage_dict, indent=4)
with open("consolidated_chia_outfile.json", "w") as outfile:
    outfile.write(json_obj)

        
