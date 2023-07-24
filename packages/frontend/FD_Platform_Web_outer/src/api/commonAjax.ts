import service from "../utils/axios";

export function get2dImage() {
  return service.get("https://api.1314.cool/img/sort/api/api.php?return=json");
}
